package com.negocio.controlador;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class ServletAjax
 */
public class ServletAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//implenmentacion del log4j
    private static final Logger log = LogManager.getLogger("ServletAjax : ");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAjax() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getParameter("accion");
		
		if(accion.equals("cargarImagen")){
			//System.out.println("Peticion Ajax recibida correctamente");
			String s = cargarImagen(request, "D:/REPO_JSP_SERVLET/repo_data");
			log.info(s);
		}
	
		
	}
	
	//request : Elementos de tipo archivo
	public String cargarImagen(HttpServletRequest request,String urlDestino){
		
		String valorRetorno="";
		//lista de objeto fileItem
		try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for(FileItem item : items){
				String nombreImagen = item.getName();
				long tamanioImagen = item.getSize();
				
				//valir archivo de la imagen y tamaño maximo
				
				//Para grabar la imagen
				File archivoCargado = new File(urlDestino,nombreImagen);
				//escribiendo el archivo
				item.write(archivoCargado);
				
				valorRetorno = "Imagen Cargada Correctamente";
			}
		} catch (Exception e) {
			log.error("Al cargar imagen" +e.getMessage());
			e.printStackTrace();
			valorRetorno = "Error al cargar Imagen";
		}
		
		return valorRetorno;
	}

}
