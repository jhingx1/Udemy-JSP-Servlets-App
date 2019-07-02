<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1>Ingresado como : </h1>
<!--  
<!--%

	String s = "Este mensaje no es muy dinamico";
	out.println(s);

%>
<p></p>
<!--%
	out.println(request.getHeader("USER-AGENT"));
%>
-->

<p>
	<a href="?accion=login">Iniciar Sesion</a>
</p>

<table>
	<tr>
		<td>Cerrar Session</td>
	</tr>
	<tr>
		<td>Consultar Administradores</td>
	</tr>	
</table>

<p>Contenido principal</p>

</body>
</html>