package com.negocio;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @see Servlet Recibe peticiones de internet.
 * @see Ciclo de vida del servlet : leer notas-cicloServlet
 * @author Mark1
 *
 */

public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String rutaJsp;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
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
		System.out.println(rutaJsp);
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
		if(accion!=null){
			
			if(accion.equals("login")){
				getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			}else if(accion.equals("inicio")){
				getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}else if(accion.equals("iniciarSesion")){
				getServletContext().getRequestDispatcher("/jsp/postLogin.jsp").forward(request, response);
			}
			
		}else{
			//redirigiendo hacia otra pagina
			getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String accion = request.getParameter("accion");
		if(accion!=null){
			
			if(accion.equals("iniciarSesion")){
				/*
				System.out.println("Usuario : "+request.getParameter("usuario") +
						" Contraseña : " + request.getParameter("contracena"));
				*/
				String usuario = request.getParameter("usuario");
				String contrasena = request.getParameter("contrasena");
				
				//Ambito request, una peticion solo una vez por peticion
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
				setRespuestaControlador("postLogin.jsp").forward(request, response);
				
			}
			
		}else{
			//redirigiendo hacia otra pagina
			setRespuestaControlador("index.jsp").forward(request, response);
		}
		
	}

}
