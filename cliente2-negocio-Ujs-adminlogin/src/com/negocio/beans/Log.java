package com.negocio.beans;

public class Log {

	private int idLog;
	private String accionLog;
	private int idAdmin;

	public Log() {

	}

	public Log(int idLog, String accionLog, int idAdmin) {
		super();
		this.idLog = idLog;
		this.accionLog = accionLog;
		this.idAdmin = idAdmin;
	}

	public int getIdLog() {
		return idLog;
	}

	public void setIdLog(int idLog) {
		this.idLog = idLog;
	}

	public String getAccionLog() {
		return accionLog;
	}

	public void setAccionLog(String accionLog) {
		this.accionLog = accionLog;
	}

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}

}
