package dto;

import java.sql.Date;

public class ContagioObject {
	private String cedula_principal;
	private String cedula_contacto;
	private Date reg_date;
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getCedula_contacto() {
		return cedula_contacto;
	}
	public void setCedula_contacto(String cedula_contacto) {
		this.cedula_contacto = cedula_contacto;
	}
	public String getCedula_principal() {
		return cedula_principal;
	}
	public void setCedula_principal(String cedula_principal) {
		this.cedula_principal = cedula_principal;
	}
}
