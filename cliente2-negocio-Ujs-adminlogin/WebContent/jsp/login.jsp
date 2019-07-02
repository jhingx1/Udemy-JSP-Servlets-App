<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1>Login.jsp</h1>

	<form method="post" action="?accion=iniciarSesion"><!-- <form method="get"> -->
		<table>
  			<tr> <!-- Filas -->
  				<td>Nombre Usuario</td> <!-- Columnas -->
  				<td><input type="text" name="usuario"/></td>
  			</tr>
  			<tr>
  				<td>Contraseña</td>
  				<td><input type="password" name="contrasena"/></td>
  			</tr>
  			<tr>
  				<!-- <td><input type="hidden" name="accion" value="iniciarSesion"/></td> -->
  				<td><input type="submit" value="Iniciar Sesion"/></td>
  			</tr>
  			
		</table>
	</form>
	

	<a href="?accion=inicio">Regresar</a>

</body>
</html>