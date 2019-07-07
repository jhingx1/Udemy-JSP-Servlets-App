<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>Envia Correo Electronico</h1>
	<br/>
	<form method="post" action="?accion=envioCorreo"> <!-- Esto es un formulario de tipo get, asi que enviamos accion por valor oculto -->
		
		Destino<input type="text" name="destino"  size="35"/> <br/>
		Mensaje<input type="text" name="mensaje"  size="35"/> <br/>
		
		<input type="submit" value="Enviar Correo"/>
		
	</form>

</body>
</html>