package com.novellius;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;	
	
	private String rutaJsp;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
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
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getParameter("accion");
		if(accion!=null){
			
			if(accion.equals("login")){
				getServletContext().getRequestDispatcher(rutaJsp + "login.jsp").forward(request, response);
			}else if(accion.equals("inicio")){
				getServletContext().getRequestDispatcher(rutaJsp + "index.jsp").forward(request, response);
			}
			/*
			else if(accion.equals("iniciarSesion")){
				getServletContext().getRequestDispatcher("/jsp/postLogin.jsp").forward(request, response);
			}
			*/	
			
		}else{
			//Contexto del servlet
			getServletContext().getRequestDispatcher(rutaJsp + "index.jsp").forward(request, response);
		}
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getParameter("accion");
		if(accion!=null){
			if(accion.equals("iniciarSesion")){
				//System.out.println("Usuario:"+request.getParameter("usuario")
					//+", Contrasena:" + request.getParameter("contrasena"));
				//getServletContext().getRequestDispatcher("/jsp/postLogin.jsp").forward(request, response);
				
				//paso de parametros
				String usuario = request.getParameter("usuario");
				String contrasena = request.getParameter("contrasena");
				
				//Ambito request, una peticion solo una vez
				request.setAttribute("usuario", usuario);
				request.setAttribute("contrasena", contrasena);
				
				//Ambito sesion, coloca el objeto mientras la session este abierta(ventana del navegador)
				//Revisar el ciclo del servlet
				HttpSession sesion = request.getSession();
				sesion.setAttribute("usuario", usuario);
				sesion.setAttribute("contrasena", contrasena);
								
				//Ambito Contexto, solo se destruye cuando se ejecuta el metodo detroir
				//Revisar el ciclo del servlet
				ServletContext contexto = getServletContext();
				contexto.setAttribute("usuario", usuario);
				contexto.setAttribute("contrasena", contrasena);
				
				getServletContext().getRequestDispatcher(rutaJsp + "postLogin.jsp").forward(request, response);
			}
		}	
		else{
			//Contexto del servlet
			getServletContext().getRequestDispatcher(rutaJsp + "index.jsp").forward(request, response);
		}
	}

}
