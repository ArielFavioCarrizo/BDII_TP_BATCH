package ar.edu.uca.arielcarrizo.basesdedatosii.tpbatch.arielcarrizotpbatch;

import org.springframework.batch.item.ItemProcessor;

public class PrestamoAltoFilterItemProcessor implements ItemProcessor<Prestamo, Prestamo> {

	@Override
	public Prestamo process(Prestamo item) throws Exception {
		if ( item.getMonto() > 50000.0d ) {
			return item;
		}
		else {
			return null;
		}
	}

}
