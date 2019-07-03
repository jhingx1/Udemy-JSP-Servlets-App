package com.negocio.util;

import java.security.NoSuchProviderException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class ManejadorCorreos {
	
	private Properties props; //Aqui se va a almacenar los datos del correo que se va a almacenar.
	private Session sesion; //sesion mail. establece un canal y luego cierrar el canal
	private Transport t; //variable donde se crean las conexiones.
	private MimeMessage msg; //se compone el mensaje correo
	
	public ManejadorCorreos(){
		props =new  Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.port", "25");
		
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		/*
		props.setProperty("mail.smtp.user", "jhingx1@gmail.com");
		props.setProperty("mail.smtp.password", "#sistemas#04");
		props.setProperty("mail.smtp.auth", "true");
		*/
	}
	
	
	public void enviarCorreo(String destino,String mensaje) throws MessagingException,NoSuchProviderException{
		
		//sesion = Session.getDefaultInstance(props);
		
		 sesion = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
			 protected PasswordAuthentication getPasswordAuthentication(){
			 return new PasswordAuthentication("jhingx1@gmail.com","#sistemas#04");
			 }
			 
			 });
		/*
		msg = new MimeMessage(sesion);
		msg.setFrom(new InternetAddress("jhingx1@gmail.com"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
		msg.setSubject("Tienes un nuevo correo electronico");
		
		msg.setText("<h2>" + mensaje + "</h2>", "UTF-8","html");
		
		t = sesion.getTransport("smtp");
		t.connect("jhingx1@gmail.com","#sistemas#04"); //conectando la cuenta de correo, passando password.
		t.sendMessage(msg, msg.getAllRecipients());
		t.close();
		*/
		 
		 msg = new MimeMessage(sesion);
		 msg.setFrom(new InternetAddress("jhingx1@gmail.com"));
		 msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
		 msg.setSubject("Tienes un nuevo correo electronico.");
		 msg.setText("<h2>"+ mensaje +"</h2>", "UTF-8","html");
		 
		 Transport transport = sesion.getTransport("smtp");
		 transport.connect("smtp.gmail.com", "jhingx1@gmail.com", "#sistemas#04");
	     transport.sendMessage(msg, msg.getAllRecipients());
	     transport.close();
		 /*
		 Transport.send(msg, msg.getAllRecipients());
		 t.close();
		 */
	}
	
}
