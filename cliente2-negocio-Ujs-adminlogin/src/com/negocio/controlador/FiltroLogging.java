package com.negocio.controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.negocio.model.Logging;
import com.negocio.util.Util;

/**
 * Servlet Filter implementation class FiltroLogging
 */
public class FiltroLogging implements Filter {

	//implenmentacion del log4j
    private static final Logger log = LogManager.getLogger("Proviene de la Clase FilterLogging");
    private DataSource ds;
    private Connection con;
    /**
     * Default constructor. 
     */
    public FiltroLogging() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("Peticion ha pasado por el filtro.");
		
		//CREANDO LA CONEXION CON LA BASE DE DATOS
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			//Enviar a una vista de error
			log.error("Error al crear conexion "  + e.getMessage());
		}	
		
		//Para obtener accion(que es el que identifica que accion se esta realizando)
		//es parecedio que el que esta en el servlet pero de diferente tipo asi k lo tenemos que castear.
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		
		//Obtenermos la variable accion
		String accion = servletRequest.getParameter("accion");
		//variable para la sesion
		HttpSession sesion = servletRequest.getSession();
		
		if(accion!=null){
			if(sesion.getAttribute("usuario")!=null && sesion.getAttribute("id")!=null){
				
				//Para el horas-mes-anio
				Util util = new Util();
				String fechaYHora = util.getAnio() + "/" + util.getMes() +"/" + util.getDia() + "/" + util.getHora();
				
				//El id se obtiene desde el servlet
				int idAdmin = (int) sesion.getAttribute("id");
				
				//registrando las acciones
				if(accion.equals("consultarAdministradores")){
					
					//debug-demo
					log.debug("usuario : " + sesion.getAttribute("usuario") + " , id:" + idAdmin);
					
					//obtener la conexion
					//Logging logging = new Logging(con);
					if(new Logging(con).registrarLog("Consulta Administradores : " + fechaYHora,idAdmin)){
						log.info("Log creado correctamente");
					}else{
						log.error("Error al crear el log");
					}
					
				}else if(accion.equals("registroPregunta")){
					//obtener la conexion
					//Logging logging = new Logging(con);
					if(new Logging(con).registrarLog("Formulario para registrar pregunta " 
							+ "secreta." + fechaYHora,idAdmin)){
						log.info("Log creado correctamente");
					}else{
						log.error("Error al crear el log");
					}
				}else if(accion.equals("registrarPregunta")){
					//obtener la conexion
					//Logging logging = new Logging(con);
					if(new Logging(con).registrarLog("Intento de registro secreta " + fechaYHora,idAdmin)){
						log.info("Log creado correctamente");
					}else{
						log.error("Error al crear el log");
					}
				}
				
			}
		}
		
		//Liberando la conexion
		try {
			con.close();
		} catch (Exception e) {
			log.error("Error al cerrar la conexion" + e.getMessage());
		}
		
		//encadena las conexiones del filtro al cliente
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
		//Para usar la conexion de JNDI sin necesidad de conocer el usuario y contraseña
    	try {
			InitialContext initContext = new InitialContext();
			Context env = (Context) initContext.lookup("java:comp/env");
			
			ds = (DataSource) env.lookup("jdbc/novellius");
		} catch (NamingException e) {
			log.error("Al configurar JNDI" + e.getMessage());
		}
		
	}

}
