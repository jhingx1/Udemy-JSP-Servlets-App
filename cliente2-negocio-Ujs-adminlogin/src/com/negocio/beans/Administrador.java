package com.negocio.beans;

import org.apache.commons.validator.EmailValidator;

public class Administrador {
	
	private int idAdmin;
	private String email;//lo validaremos con commoms-validator-pueva validar varias condiciones
	private String contrasena;
	private String nombre;
	private String respuesta;
	private String urlImagen;
	private int id;	
	
	//Para informar al usuario los errores de la clase
	private StringBuilder sb;
	
	public String getErroresForm(){
		return (sb != null) ? sb.toString():new StringBuilder().toString();
	}
	
	public Administrador(int idAdmin, String email, String contrasena, String nombre, String respuesta,
			String urlImagen, int id) {		
		this.idAdmin = idAdmin;
		this.email = email;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.respuesta = respuesta;
		this.urlImagen = urlImagen;
		this.id = id;
	}
	
	

	public Administrador(String email, String contrasena, String nombre, String respuesta, String urlImagen, int id) {		
		this.email = email;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.respuesta = respuesta;
		this.urlImagen = urlImagen;
		this.id = id;
	}



	public Administrador() {
		//constructor vacio.
	}
	
	//validaciones
	public boolean isValidAdministrador(){
		
		sb = new StringBuilder();//msg de error
		
		boolean valorRetorno = false;
		
		EmailValidator emailValidator = EmailValidator.getInstance();
		//si la condicion no se cumple
		if(!emailValidator.isValid(email)){
			sb.append("Email invalido");
			valorRetorno = false;
		}
		
		if(contrasena.equals("")){
			sb.append("Contraseña no puede esta vacia");
			valorRetorno = false;
		}else if(contrasena.length() < 3){
			sb.append("Constraseña debe tener minimo 4 caracteres");
			valorRetorno = false;
		}
		
		if(nombre.equals("")){
			sb.append("Nombre no puede esta vacia");
			valorRetorno = false;
		}
		
		if(sb.length() == 0){
			valorRetorno = true;
		}
		
		return valorRetorno;
	}
	

	@Override
	public String toString() {		
		return super.toString();
	}
	
	public int getIdAdmin() {
		return idAdmin;
	}
	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getUrlImagen() {
		return urlImagen;
	}
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
