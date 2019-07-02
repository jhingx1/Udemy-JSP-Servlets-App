package com.negocio.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.negocio.beans.Administrador;

//los model son los que interactuan con la base de datos
public class Cuenta {
	
	private Connection con;
	//para el log4j
	private static final Logger log = LogManager.getLogger("Cuenta : ");
	
	//conexion
	public Cuenta(Connection con){		
		this.con = con;
	}
	
	//para realizar el login : es se conexta con el servlet
	public boolean login(String email,String contrasena){
		
		//retornar el numero de filas que coinsiden con la consulta
		String sql = "select count(*) as count from administrador where email = ? and contrasena = ?";
		int cta = 0;
		
		//conexion
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, email);
			st.setString(2,contrasena);
			
			//almacenar la consulta
			ResultSet rs = st.executeQuery();
			//verificando si la consutla tiene filas
			if(rs.next()){
				cta = rs.getInt("count");
			}
			
			rs.close();
		} catch (SQLException e) {
			log.error("Error al realizar un login : " + e.getMessage());
			return false;
		}
		
		if(cta == 0){
			return false;
		}else {
			return true;
		}
		
	}
	
	//Debuelve un arrayList con los objetos de tipo administrador
	public ArrayList<Administrador> consultarAdministradores() {

		// creando un arrayalist que aceptar objetos de tipo administradores
		ArrayList<Administrador> administradores = new ArrayList<Administrador>();

		String sql = "select * from administrador";

		try {
			PreparedStatement st = con.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			// este tiene valores y necesitamos atrapar varios valores
			// hay que iterar en las filas
			while (rs.next()) {

				// creando una instancia de administradores
				// este ojteto guarda los datos de una fila de base de datos
				Administrador administrador = new Administrador(
						rs.getString("email"), 
						rs.getString("contrasena"),
						rs.getString("nombre"), 
						rs.getString("estado"), 
						rs.getInt("id"));

				// agregando a el arrayList
				// este arrayList tendra a los tres administradores
				administradores.add(administrador);

			}
			rs.close();
		} catch (SQLException e) {
			// si ubo una excepcion debo de borrar todo lo que esta en el
			// arrayList
			administradores.clear();
			e.printStackTrace();
			log.error("Al consultar administradores " + e.getMessage());
		}
		return administradores;
	}
	
	
}