package com.negocio.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Cuenta {
	
	private Connection con;
	//para el log4j
	private static final Logger log = LogManager.getLogger("Cuenta : ");
	
	//conexion
	public Cuenta(Connection con){
		super();
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
	
	
}