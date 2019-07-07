package com.negocio.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *Metodo para insertar el log en nuestra base de datos
 * @author Mark1
 *
 */

public class Logging {	

	private Connection con;
	//para el log4j
	private static final Logger log = LogManager.getLogger("Logging : ");
	
	//conexion
	public Logging(Connection con){		
		this.con = con;
	}
	
	public boolean registrarLog(String accionLog,int idAdmin){
		
		String sql ="insert into log(accionLog,idAdmin) values (?,?)";
		
		try {
			PreparedStatement st = con.prepareStatement(sql);			
			st.setString(1,accionLog);
			st.setInt(2,idAdmin);
			st.executeUpdate();
			st.close();
			
			return true; //si todo va bien
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
}
