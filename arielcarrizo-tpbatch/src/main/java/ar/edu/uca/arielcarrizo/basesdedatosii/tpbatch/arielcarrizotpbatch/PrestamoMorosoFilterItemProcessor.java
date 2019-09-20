package ar.edu.uca.arielcarrizo.basesdedatosii.tpbatch.arielcarrizotpbatch;

import java.util.Calendar;

import org.springframework.batch.item.ItemProcessor;

public class PrestamoMorosoFilterItemProcessor implements ItemProcessor<Prestamo, Prestamo> {
	private long currentTimestamp;
	
	public PrestamoMorosoFilterItemProcessor() {
		this.currentTimestamp = Calendar.getInstance().getTimeInMillis();
	}
	
	@Override
	public Prestamo process(Prestamo item) throws Exception {
		if ( item.getFechaVencimiento().getTime() < this.currentTimestamp ) {
			return item;
		}
		else {
			return null;
		}
	}

}
