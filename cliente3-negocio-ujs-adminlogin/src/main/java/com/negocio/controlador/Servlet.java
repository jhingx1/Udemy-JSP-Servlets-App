package com.negocio.controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Servlet
 */
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;	

	public Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("accion");

		if (accion != null) {

			if (accion.equals("login")) {
				getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			} else if (accion.equals("inicio")) {
				getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}else if (accion.equals("iniciarSession")) {
				getServletContext().getRequestDispatcher("/jsp/postLogin.jsp").forward(request, response);
			}
		} else {
			getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
request.setCharacterEncoding("UTF-8");//Para que respete las tildes, el correo electronico
		
		
		
	}

}
