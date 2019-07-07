package com.negocio.controlador;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
//import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.negocio.model.Cuenta;
import com.negocio.util.ManejadorCorreos;
import com.negocio.util.VerificarRecaptcha;
import com.negocio.beans.Administrador;

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
		
		//CREANDO LA CONEXION CON LA BASE DE DATOS
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			//Enviar a una vista de error
			log.error("Error al crear conexion "  + e.getMessage());
		}	
		
		if(accion!=null){
			
			if(accion.equals("login")){
				getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			}else if(accion.equals("inicio")){
				getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}else if(accion.equals("logout")){	 //para destruir la sesscion.	
				sesion.invalidate();
				log.info("sesion destruida");
				setRespuestaControlador("login").forward(request, response);
			}else if(accion.equals("consultarAdministradores")){ //desde el index
				
				//Cuenta cuenta = new Cuenta(con); se puede simplificar con la instanciacion anonima
				ArrayList<Administrador> administradores = new Cuenta(con).consultarAdministradores();
				
				if(administradores.isEmpty()){ //si esta vacio
					//se imprimira en una vista jsp que se creara
					request.setAttribute("mensaje", "No se encotraron administradores");
				}else{
					//esto se colocara en el ambito_scopes(que existen 3) de una sesion					
					request.setAttribute("mensaje", "Administradores encontrados");		
					//para comunicarlo con la vista jsp
					sesion.setAttribute("administradores", administradores);						
				}				
				//redirigiendo a una pagina jsp, asi no encuetre administradores.				
				setRespuestaControlador("consultaAdministradores").forward(request, response);
				
			}else if(accion.equals("registroPregunta")){//para registrar una pregunta (forma transaccional)
				setRespuestaControlador(accion).forward(request, response);				
			}else if(accion.equals("envioCorreo")){
				setRespuestaControlador(accion).forward(request, response);
			}else if(accion.equals("registroAdmin")){
				setRespuestaControlador(accion).forward(request, response);
			}			
		}else{
			//redirigiendo hacia otra pagina
			getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
		
	}
	/*
	else if(accion.equals("registrarPregunta")){
				setRespuestaControlador(accion).forward(request, response);
			}
	*/

	/**
	 * @see 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");//Para que respete las tildes, el correo electronico
		
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
				
				//implementando las cookies - recordar contraseña
				//el formulario envia todo lo que esta dentro de el asi k si no encuentra un parametro 
				//con el name = ckbox genera error - por eso se le coloca el try catch 
				try {
					//si esta marcado
					if(request.getParameter("ckbox").equals("on")){
						//creamos un cookie
						Cookie cookie = new Cookie("usuario",usuario);
						cookie.setMaxAge(60*60*24); //timepo de vida
						response.addCookie(cookie);
						log.info("Cookie creada");
					}
				} catch (NullPointerException e) {
					log.info("No marco el check");
					//mejorar el codigo para el cookie
				}
				
				
				//para la validacion del usurio - clase model 
				Cuenta cuenta = new Cuenta(con);
				if(cuenta.login(usuario, contrasena)){					
					log.info("Ingresado correctamente como: " +  usuario);
					
					sesion.setAttribute("usuario", usuario);
					sesion.setAttribute("contrasena", contrasena);
				
					//logging-necesitamos que el id del usuario este en la session, lo usamos en el loggin
					//Como necesitamos el id del usuario hay que obtener el usuario por consulta(no recomendado)
					//en la clase cuenta(dao-conexion base de datos)
					sesion.setAttribute("id",new Cuenta(con).obtenerIdAdmin(usuario));
					
					//redirigir el controlador al index
					setRespuestaControlador("index").forward(request, response);
					
				}else{					
					log.error("error login");
					//error en la amisma pagina
					request.setAttribute("error", "Nombre de usuario o Contraseña incorrectos");
					setRespuestaControlador("login").forward(request, response);
				}	
				
				
			}else if(accion.equals("envioCorreo")){
				ManejadorCorreos manejadorCorreos = new ManejadorCorreos();
				try {
					manejadorCorreos.enviarCorreo(request.getParameter("destino"),request.getParameter("mensaje"));
					log.info("Correo Enviado Correctamente");
					request.setAttribute("mensaje", "Correo Electronico enviado Correctamente");
				} catch (Exception e) {		
					log.error("Al enviar correo : "+ e.getMessage());
					e.printStackTrace();
					//setRespuestaControlador("errorCorreo").forward(request, response);
					request.setAttribute("mensaje", "Error al Enviar Correo Electronico");
				}
				setRespuestaControlador("postEnvioCorreo").forward(request, response);
			}else if(accion.equals("registrarAdmin")){//Para registrar un nuevo administrador
				Administrador administrador = new Administrador();
				administrador.setEmail(request.getParameter("email"));
				administrador.setContrasena(request.getParameter("contrasena"));
				administrador.setNombre(request.getParameter("nombre"));
				administrador.setRespuesta(request.getParameter("respuesta"));
				
				if (sesion.getAttribute("urlImagen") != null) {
					//para cargar la imagen
					if (!sesion.getAttribute("urlImagen").equals("")) {
						administrador.setUrlImagen((String) sesion.getAttribute("urlImagen"));
					} 
				}
				administrador.setId(Integer.parseInt(request.getParameter("pregunta")));
				
				//para insertar un administrador
				Cuenta cuenta = new Cuenta(con);
				
				if (administrador.isValidAdministrador()) { //validando datos
					if (!cuenta.existeAdministrador(request.getParameter("email"))) {//Para validar que no se cre dos email igules
						if (cuenta.registrarAdministrador(administrador)) {

							log.info("Ingresado correctamente como: ");

							request.setAttribute("msg", "administrador credo correctamente");

						} else {
							log.error("El admin no fue creado");
							//error en la amisma pagina
							request.setAttribute("msg", "Error al crear el administrador");
						}
					} else {
						log.error("el admin ya fue creado");
						//error en la amisma pagina
						request.setAttribute("msg", "Admin. duplicado");
					} 
				}else{
					request.setAttribute("msg", administrador.getErroresForm());
				}
				
				setRespuestaControlador("registroAdmin").forward(request, response);
				
			}else if(accion.equals("registrarPregunta")){
				
				String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		        System.out.println(gRecaptchaResponse);
		        
		        boolean verificado = VerificarRecaptcha.verificar(gRecaptchaResponse);
		        System.out.println("captcha: " + verificado);
				
		        if(verificado){
		        	setRespuestaControlador(accion).forward(request, response);
		        }else{
		        	
		        	request.setAttribute("error", "*ReCapcha invalido Intentar Nuevamente");
		        	setRespuestaControlador("registroPregunta").forward(request, response);
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
package com.negocio.controlador;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
//import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.negocio.model.Cuenta;
import com.negocio.util.ManejadorCorreos;
import com.negocio.beans.Administrador;

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
		
		//CREANDO LA CONEXION CON LA BASE DE DATOS
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			//Enviar a una vista de error
			log.error("Error al crear conexion "  + e.getMessage());
		}	
		
		if(accion!=null){
			
			if(accion.equals("login")){
				getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			}else if(accion.equals("inicio")){
				getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}else if(accion.equals("logout")){	 //para destruir la sesscion.	
				sesion.invalidate();
				log.info("sesion destruida");
				setRespuestaControlador("login").forward(request, response);
			}else if(accion.equals("consultarAdministradores")){ //desde el index
				
				//Cuenta cuenta = new Cuenta(con); se puede simplificar con la instanciacion anonima
				ArrayList<Administrador> administradores = new Cuenta(con).consultarAdministradores();
				
				if(administradores.isEmpty()){ //si esta vacio
					//se imprimira en una vista jsp que se creara
					request.setAttribute("mensaje", "No se encotraron administradores");
				}else{
					//esto se colocara en el ambito_scopes(que existen 3) de una sesion					
					request.setAttribute("mensaje", "Administradores encontrados");		
					//para comunicarlo con la vista jsp
					sesion.setAttribute("administradores", administradores);						
				}				
				//redirigiendo a una pagina jsp, asi no encuetre administradores.				
				setRespuestaControlador("consultaAdministradores").forward(request, response);
				
			}else if(accion.equals("registroPregunta")){//para registrar una pregunta (forma transaccional)
				setRespuestaControlador(accion).forward(request, response);				
			}else if(accion.equals("registrarPregunta")){
				setRespuestaControlador(accion).forward(request, response);
			}else if(accion.equals("envioCorreo")){
				setRespuestaControlador(accion).forward(request, response);
			}else if(accion.equals("registroAdmin")){
				setRespuestaControlador(accion).forward(request, response);
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
		
		request.setCharacterEncoding("UTF-8");//Para que respete las tildes, el correo electronico
		
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
				
				//implementando las cookies - recordar contraseña
				//el formulario envia todo lo que esta dentro de el asi k si no encuentra un parametro 
				//con el name = ckbox genera error - por eso se le coloca el try catch 
				try {
					//si esta marcado
					if(request.getParameter("ckbox").equals("on")){
						//creamos un cookie
						Cookie cookie = new Cookie("usuario",usuario);
						cookie.setMaxAge(60*60*24); //timepo de vida
						response.addCookie(cookie);
						log.info("Cookie creada");
					}
				} catch (NullPointerException e) {
					log.info("No marco el check");
					//mejorar el codigo para el cookie
				}
				
				
				//para la validacion del usurio - clase model 
				Cuenta cuenta = new Cuenta(con);
				if(cuenta.login(usuario, contrasena)){					
					log.info("Ingresado correctamente como: " +  usuario);
					
					sesion.setAttribute("usuario", usuario);
					sesion.setAttribute("contrasena", contrasena);
				
					//logging-necesitamos que el id del usuario este en la session, lo usamos en el loggin
					//Como necesitamos el id del usuario hay que obtener el usuario por consulta(no recomendado)
					//en la clase cuenta(dao-conexion base de datos)
					sesion.setAttribute("id",new Cuenta(con).obtenerIdAdmin(usuario));
					
					//redirigir el controlador al index
					setRespuestaControlador("index").forward(request, response);
					
				}else{					
					log.error("error login");
					//error en la amisma pagina
					request.setAttribute("error", "Nombre de usuario o Contraseña incorrectos");
					setRespuestaControlador("login").forward(request, response);
				}	
				
				
			}else if(accion.equals("envioCorreo")){
				ManejadorCorreos manejadorCorreos = new ManejadorCorreos();
				try {
					manejadorCorreos.enviarCorreo(request.getParameter("destino"),request.getParameter("mensaje"));
					log.info("Correo Enviado Correctamente");
					request.setAttribute("mensaje", "Correo Electronico enviado Correctamente");
				} catch (Exception e) {		
					log.error("Al enviar correo : "+ e.getMessage());
					e.printStackTrace();
					//setRespuestaControlador("errorCorreo").forward(request, response);
					request.setAttribute("mensaje", "Error al Enviar Correo Electronico");
				}
				setRespuestaControlador("postEnvioCorreo").forward(request, response);
			}else if(accion.equals("registrarAdmin")){//Para registrar un nuevo administrador
				Administrador administrador = new Administrador();
				administrador.setEmail(request.getParameter("email"));
				administrador.setContrasena(request.getParameter("contrasena"));
				administrador.setNombre(request.getParameter("nombre"));
				administrador.setRespuesta(request.getParameter("respuesta"));
				
				if (sesion.getAttribute("urlImagen") != null) {
					//para cargar la imagen
					if (!sesion.getAttribute("urlImagen").equals("")) {
						administrador.setUrlImagen((String) sesion.getAttribute("urlImagen"));
					} 
				}
				administrador.setId(Integer.parseInt(request.getParameter("pregunta")));
				
				//para insertar un administrador
				Cuenta cuenta = new Cuenta(con);
				
				if (administrador.isValidAdministrador()) { //validando datos
					if (!cuenta.existeAdministrador(request.getParameter("email"))) {//Para validar que no se cre dos email igules
						if (cuenta.registrarAdministrador(administrador)) {

							log.info("Ingresado correctamente como: ");

							request.setAttribute("msg", "administrador credo correctamente");

						} else {
							log.error("El admin no fue creado");
							//error en la amisma pagina
							request.setAttribute("msg", "Error al crear el administrador");
						}
					} else {
						log.error("el admin ya fue creado");
						//error en la amisma pagina
						request.setAttribute("msg", "Admin. duplicado");
					} 
				}else{
					request.setAttribute("msg", administrador.getErroresForm());
				}
				
				setRespuestaControlador("registroAdmin").forward(request, response);
				
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
