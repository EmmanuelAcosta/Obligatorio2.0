package dto;

import java.util.Date;

public class CupoObject {
	private String lugar;
	private int estado;
	private Date fec_primer_dosis;
	private Date fec_segunda_dosis;
	private String codigo_reserva;
	private String localidad;
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int i) {
		this.estado = i;
	}
	public Date getFec_primer_dosis() {
		return fec_primer_dosis;
	}
	public void setFec_primer_dosis(Date fec_primer_dosis) {
		this.fec_primer_dosis = fec_primer_dosis;
	}
	public Date getFec_segunda_dosis() {
		return fec_segunda_dosis;
	}
	public void setFec_segunda_dosis(Date fec_segunda_dosis) {
		this.fec_segunda_dosis = fec_segunda_dosis;
	}
	public String getCodigo_reserva() {
		return codigo_reserva;
	}
	public void setCodigo_reserva(String codigo_reserva) {
		this.codigo_reserva = codigo_reserva;
	}
	
	
}
