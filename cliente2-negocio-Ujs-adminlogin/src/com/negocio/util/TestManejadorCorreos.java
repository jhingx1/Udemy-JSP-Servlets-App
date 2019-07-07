package com.negocio.util;

import java.security.NoSuchProviderException;

import javax.mail.MessagingException;

//Acceso de apps menos seguras -> https://myaccount.google.com/lesssecureapps -> Seria bueno que al terminar cambiemos de opcion.

public class TestManejadorCorreos {

	//Para realizar mejoras al envio de mail
	//https://kodejava.org/how-do-i-send-an-html-email/
	//https://www.tutorialspoint.com/javamail_api/javamail_api_send_html_in_email.htm
	
	public static void main(String[] args) {
		
		ManejadorCorreos manejadorCorreos = new ManejadorCorreos();
		try {
			manejadorCorreos.enviarCorreo("jhingx1@outlook.com", "Prueba de envio de correo desde Java - 2019");
			System.out.println("Correo enviado");
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
