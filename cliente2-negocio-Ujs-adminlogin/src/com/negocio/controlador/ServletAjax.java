package com.negocio.controlador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    
    //obteniendo el nombre de la imagen
    private String nombreImagen = "";
       
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
		
		request.setCharacterEncoding("UTF-8");//idioma
		response.setContentType("text/html; charset-UTF-8");
		PrintWriter out = response.getWriter();//Para devolverele el texto a la funcion ajax-> responseText
		
		String accion = request.getParameter("accion");
		
		//instacia de la sesscion
		HttpSession sesion = request.getSession();
		
		if(accion.equals("cargarImagen")){
			//System.out.println("Peticion Ajax recibida correctamente");
			String s = cargarImagen(request, "C:/Users/Mark1/Documents/GitHub/cliente2-negocio-Ujs-adminlogin/cliente2-negocio-Ujs-adminlogin/WebContent/imagenes");
			
			//sesion atibuto,
			//para guardar en la base de datos, la ruta de la imagen.
			sesion.setAttribute("urlImagen", "/imagenes/"+nombreImagen);
			
			log.info(s);
			out.println(s);
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
				if(isImagenValida(item)){
					
					//tamaño de la imagen
					if(tamanioImagen > 0 && tamanioImagen < 524880){ //en bytes
						//Para grabar la imagen
						File archivoCargado = new File(urlDestino,nombreImagen);
						//escribiendo el archivo
						item.write(archivoCargado);
						
						//para que sea igual a la variable imagen, con esto obtenemos el nombre de la imagen
						this.nombreImagen = nombreImagen;
						
						valorRetorno = "<span style='color:green;'>Imagen Cargada Correctamente</span>";
					}else{
						valorRetorno ="<span style='color:red;'>Tamaño maximo de la imagen es 5 MB</span>";
					}						
				}else{
					valorRetorno ="<span style='color:red;'>El archivo a cargar no es una imagen</span>";
				}
				
			}
		} catch (Exception e) {
			log.error("Al cargar imagen" +e.getMessage());
			e.printStackTrace();
			valorRetorno = "<span style='color:red;'>Error al cargar Imagen</span>";
		}
		
		return valorRetorno;
	}
	
	//para validar una imagen y el tamaño
	public boolean isImagenValida(FileItem archivo){
		
		String nombre = archivo.getName();
		
		if(!(nombre.isEmpty())){
			
			//extencion de la imagen
			String extencion3 = nombre.substring(nombre.length()-3,nombre.length());
			String extencion4 = nombre.substring(nombre.length()-4,nombre.length());
			
			if(extencion3.equals("jpg") || extencion3.equals("png")){
				return true;
			}else if(extencion4.equals("JPEG") || extencion4.equals("TIFF")){
				return true;
			}else{
				return false;
			}
			
			
		}else{
			return false;
		}
				
	}
	
}
