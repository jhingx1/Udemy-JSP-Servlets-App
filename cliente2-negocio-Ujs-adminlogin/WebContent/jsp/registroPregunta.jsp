<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src='https://www.google.com/recaptcha/api.js'></script>
<title>Pregunta Secreta</title>
</head>
<body>
	<h1>Registro pregunta secreta</h1>
	<br/>
	<c:out value="${error}"/>
	<form method="post" action="?accion=registrarPregunta"> <!-- Esto es un formulario de tipo get, asi que enviamos accion por valor oculto -->
		Captura la pregunta secreta : <br/>
		
		<input type="text" name="pregunta"  size="35"/> <br/>
		<!--  <input type="hidden" name="accion" value="registrarPregunta"/>  -->
		<%-- String accion = registrarPregunta --%>
		<div class="g-recaptcha" data-sitekey="6LcUa6wUAAAAAOnPdfg1zTUkROJK2lSb8Oi9KIX2"></div>
		<input type="submit" value="registrar" />
		
	</form>
</body>
</html>