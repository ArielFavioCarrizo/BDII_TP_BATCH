package ar.edu.uca.arielcarrizo.basesdedatosii.tpbatch.arielcarrizotpbatch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public FlatFileItemReader<Prestamo> reader() {
		return new FlatFileItemReaderBuilder<Prestamo>()
				.name("prestamoItemReader")
				.resource(new ClassPathResource("business-data.csv"))
				.delimited()
				.names(new String[] {"nombre", "apellido", "dni", "fechaComienzo", "fechaDeVencimiento", "saldo"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Prestamo>() {{
					setTargetType(Prestamo.class);
				}})
				.build();
	}
	
	@Bean
	public PrestamoAltoFilterItemProcessor prestamoAltoFilterItemProcessor() {
		return new PrestamoAltoFilterItemProcessor();
	}
	
	@Bean
	public PrestamoMorosoFilterItemProcessor prestamoMorosoFilterItemProcessor() {
		return new PrestamoMorosoFilterItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Prestamo> prestamoWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Prestamo>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO prestamos (nombre, apellido, dni, fechaComienzo, fechaVencimiento, monto) VALUES (:nombre, :apellido, :dni, :fechaComienzo, :fechaVencimiento, :monto)")
				.dataSource(dataSource)
				.build();
	}
	
	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}
	
	@Bean
	public Step step1(JdbcBatchItemWriter<Prestamo> writer) {
		DefaultTransactionAttribute attribute = new DefaultTransactionAttribute();
		
		attribute.setPropagationBehavior(Propagation.REQUIRED.value());
		attribute.setIsolationLevel(Isolation.REPEATABLE_READ.value());
		attribute.setTimeout(5000);
		
		return stepBuilderFactory.get("step1")
				.<Prestamo, Prestamo> chunk(10)
				.reader(reader())
				.processor(prestamoAltoFilterItemProcessor())
				.processor(prestamoMorosoFilterItemProcessor())
				.writer(writer)
				.faultTolerant()
				.skipLimit(4)
				.skip(FlatFileParseException.class)
				.retryLimit(3)
				.retry(DeadlockLoserDataAccessException.class)
				.transactionAttribute(attribute)
				.build();
	}
}
