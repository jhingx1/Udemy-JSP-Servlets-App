package com.negocio.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class VerificarRecaptcha {

	public static final String url = "https://www.google.com/recaptcha/api/siteverify";
	public static final String secreto = "6LesaqwUAAAAAF_UJ7_cCoKmlx2wCKMf5oLWiIEL";
	private final static String USER_AGENT = "Chrome/51.0.2704.103";

	public static boolean verificar(String gRecaptchaResponse) throws IOException {
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}
		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String postParams = "secret=" + secreto + "&response=" + gRecaptchaResponse;
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			System.out.println("Enviando 'POST' peticion a URL : " + url);
			System.out.println("Parametros post : " + postParams);
			System.out.println("Codigo de respuesta : " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer respuesta = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				respuesta.append(inputLine);
			}
			in.close();
			System.out.println(respuesta.toString());
			JsonReader jsonReader = Json.createReader(new StringReader(respuesta.toString()));
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();
			return jsonObject.getBoolean("success");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}