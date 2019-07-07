<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pregunta Secreta</title>
</head>
<body>
	<h1>Registro pregunta secreta</h1>
	<br/>
	<form> <!-- Esto es un formulario de tipo get, asi que enviamos accion por valor oculto -->
		Captura la pregunta secreta : <br/>
		
		<input type="text" name="pregunta"  size="35"/> <br/>
		<input type="hidden" name="accion" value="registrarPregunta"/>
		<%-- String accion = registrarPregunta --%>
		<input type="submit" value="registrar"/>
		
	</form>
</body>
</html>