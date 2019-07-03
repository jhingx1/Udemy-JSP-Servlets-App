package com.negocio.util;

import java.security.NoSuchProviderException;

import javax.mail.MessagingException;

public class TestManejadorCorreos {

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
