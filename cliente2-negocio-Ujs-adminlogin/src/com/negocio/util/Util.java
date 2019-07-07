package com.negocio.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Util {
	
	private Calendar calendar;
	
	public Util(){
		this.calendar = new GregorianCalendar();
	}	
	
	public String getAnio(){
		//nos debuelve el anio-String.valuesOf : para que nos devuelva en cadenas
		return String.valueOf(calendar.get(Calendar.YEAR));
	}
	
	public String getMes(){
		int mesEntero = calendar.get(Calendar.MONDAY) + 1;
		String mes = "";
		switch (mesEntero) {
		case 1:
			mes = "ENE";
			break;
		case 2:
			mes = "FEB";
			break;
		case 3:
			mes = "MAR";
			break;
		case 4:
			mes = "ABR";
			break;
		case 5:
			mes = "MAY";
			break;
		case 6:
			mes = "JUN";
			break;
		case 7:
			mes = "JUL";
			break;
		case 8:
			mes = "AGO";
			break;
		case 9:
			mes = "SEP";
			break;
		case 10:
			mes = "OCT";
			break;
		case 11:
			mes = "NOB";
			break;
		case 12:
			mes = "DIC";
			break;
		default:
			break;
		}
		return mes;
	}

	public String getDia(){
		if(calendar.get(Calendar.DAY_OF_MONTH) <= 9){
			return "0"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		}else{
			return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		}
	}
	
	public String getHora(){
		if(calendar.get(Calendar.MINUTE)<=9){
			return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)+":0"+String.valueOf(Calendar.MINUTE));
		}else{
			return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)+":"+String.valueOf(Calendar.MINUTE));
		}
	}
	
}
