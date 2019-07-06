package com.negocio.demos;

public class DemosComcepto {

	public static void main(String[] args) {
		
		String nombre="imagen.png";
		//otenemos la extencion imagen
		String parte = nombre.substring(nombre.length()-3, nombre.length());
		System.out.println(nombre);
		System.out.println(parte);
		
		

	}

}
