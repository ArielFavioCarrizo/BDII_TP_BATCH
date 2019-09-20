package ar.edu.uca.arielcarrizo.basesdedatosii.tpbatch.arielcarrizotpbatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if ( jobExecution.getStatus() == BatchStatus.COMPLETED ) {
			log.info("Job finished");
			
			jdbcTemplate.query("SELECT nombre, apellido, dni, fechaComienzo, fechaVencimiento, monto FROM prestamos",
					(rs, row) -> new Prestamo(
							rs.getString(1),
							rs.getString(2),
							rs.getLong(3),
							rs.getDate(4),
							rs.getDate(5),
							rs.getDouble(6))
			).forEach(prestamo -> log.info("Found <" + prestamo + "> in the database"));
		}
	}
}
