package ar.edu.uca.arielcarrizo.basesdedatosii.tpbatch.arielcarrizotpbatch;

import java.util.Date;

public class Prestamo {
	private String nombre;
	private String apellido;
	
	private long dni;

	private Date fechaComienzo;
	private Date fechaVencimiento;
	
	private double monto;
	
	public Prestamo() {
		
	}
	
	public Prestamo(String nombre, String apellido, long dni, Date fechaComienzo, Date fechaVencimiento, double saldo) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.fechaComienzo = fechaComienzo;
		this.fechaVencimiento = fechaVencimiento;
		this.monto = saldo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public long getDni() {
		return dni;
	}

	public void setDni(long dni) {
		this.dni = dni;
	}

	public Date getFechaComienzo() {
		return fechaComienzo;
	}

	public void setFechaComienzo(Date fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + (int) (dni ^ (dni >>> 32));
		result = prime * result + ((fechaComienzo == null) ? 0 : fechaComienzo.hashCode());
		result = prime * result + ((fechaVencimiento == null) ? 0 : fechaVencimiento.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		long temp;
		temp = Double.doubleToLongBits(monto);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Prestamo))
			return false;
		Prestamo other = (Prestamo) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (dni != other.dni)
			return false;
		if (fechaComienzo == null) {
			if (other.fechaComienzo != null)
				return false;
		} else if (!fechaComienzo.equals(other.fechaComienzo))
			return false;
		if (fechaVencimiento == null) {
			if (other.fechaVencimiento != null)
				return false;
		} else if (!fechaVencimiento.equals(other.fechaVencimiento))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (Double.doubleToLongBits(monto) != Double.doubleToLongBits(other.monto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Prestamo [nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", fechaComienzo="
				+ fechaComienzo + ", fechaVencimiento=" + fechaVencimiento + ", saldo=" + monto + "]";
	}
}
