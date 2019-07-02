package com.negocio;

import java.io.IOException;
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.negocio.model.Cuenta;

/**
 * @see Servlet Recibe peticiones de internet.
 * @see Ciclo de vida del servlet : leer notas-cicloServlet
 * @author Mark1
 *
 */

public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String rutaJsp;
	//implenmentacion del log4j
    private static final Logger log = LogManager.getLogger("Servlet : ");
   
    //conexion con la vista
  	private DataSource ds;
  	private Connection con;
   
    public Servlet() {
        super();        
    }
    
    //Implementar el metodo init, para inicializar parametros iniciales
    @Override
	public void init(ServletConfig config) throws ServletException {	
		super.init(config);
		//Parametro inicial
		//System.out.println(config.getInitParameter("rutaJsp"));//esta mapeado en el web.xml
		rutaJsp = config.getInitParameter("rutaJsp");
		//System.out.println(rutaJsp);
		
		//Uso de Log4j
		BasicConfigurator.configure();
    	log.info("ruta jsp : " + rutaJsp);
    	
    	//Para usar la conexion de JNDI sin necesidad de conocer el usuario y contraseña
    	try {
			InitialContext initContext = new InitialContext();
			Context env = (Context) initContext.lookup("java:comp/env");
			
			ds = (DataSource) env.lookup("jdbc/novellius");
		} catch (NamingException e) {
			log.error("Al configurar JNDI" + e.getMessage());
		}
		
	}
    
    //para mejorar la redirccion de la ruta.
    //metodo propio
    public RequestDispatcher setRespuestaControlador(String vista){
    	String url = rutaJsp + vista + ".jsp";
    	return getServletContext().getRequestDispatcher(url);
    }

	/**
	 * @see 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//obtener una variable accion request:esta implicito en jsp y servlet
		String accion = request.getParameter("accion");	
		
		//Creando un session - luego la vamos a destruir
		HttpSession sesion = request.getSession();
		
		if(accion!=null){
			
			if(accion.equals("login")){
				getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			}else if(accion.equals("inicio")){
				getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}else if(accion.equals("logout")){	 //para destruir la sesscion.	
				sesion.invalidate();
				log.info("sesion destruida");
				setRespuestaControlador("login").forward(request, response);
			}
			
		}else{
			//redirigiendo hacia otra pagina
			getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String accion = request.getParameter("accion");
		
		//variable para la sesion
		HttpSession sesion = request.getSession();
		
		//creando la conexion
		try {
			con = ds.getConnection();
		} catch (Exception e) {
			log.error("Error al crear la conexion" + e.getMessage());
		}
		
		if(accion!=null){
			
			if(accion.equals("iniciarSession")){
				/*
				System.out.println("Usuario : "+request.getParameter("usuario") +
						" Contraseña : " + request.getParameter("contracena"));
				*/
				String usuario = request.getParameter("usuario");
				String contrasena = request.getParameter("contrasena");
				
				/*//Ambito request, una peticion solo una vez por peticion
				request.setAttribute("usuario", usuario);
				request.setAttribute("contrasena", contrasena);
				
				//Ambito sesion, coloca el objeto mientras la session este abierta(ventana del navegador)
				//Revisar el ciclo del servlet
				HttpSession sesion = request.getSession();
				sesion.setAttribute("usuario", usuario);
				sesion.setAttribute("contrasena", contrasena);
								
				//Ambito Contexto, solo se destruye cuando se ejecuta el metodo distroit
				//Revisar el ciclo del servlet
				ServletContext contexto = getServletContext();
				contexto.setAttribute("usuario", usuario);
				contexto.setAttribute("contrasena", contrasena);
				
				//redirigiendo hacia otra pagina
				setRespuestaControlador("postLogin.jsp").forward(request, response);*/
				
				Cuenta cuenta = new Cuenta(con);
				if(cuenta.login(usuario, contrasena)){					
					log.info("Ingresado correctamente como: " +  usuario);
					
					sesion.setAttribute("usuario", usuario);
					sesion.setAttribute("contrasena", contrasena);
					
					//redirigir el controlador al index
					setRespuestaControlador("index").forward(request, response);
					
				}else{					
					log.error("error login");
					//error en la amisma pagina
					request.setAttribute("error", "Nombre de usuario o Contraseña incorrectos");
					setRespuestaControlador("login").forward(request, response);
				}	
				
				
			}
			
		}else{
			//redirigiendo hacia otra pagina
			//setRespuestaControlador("index.jsp").forward(request, response);
			setRespuestaControlador("login.jsp").forward(request, response);
		}
		
		//Liberando la conexion
		try {
			con.close();
		} catch (Exception e) {
			log.error("Error al cerrar la conexion" + e.getMessage());
		}
		
	}

}
