<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">

<!-- <script src="js/lib/jquery-2.1.4.min.js"></script> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>

<title>Usuario</title>
</head>
<body>

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
<!-- 
<p>
	<a href="?accion=login">Iniciar Sesion</a>
</p>
 -->
 
<div class="container">
	<div class="panel panel-login">
		<div class="panel-heading">
			<div class="row">
				<div class="col-xs-6">
					<h3>Ingresado como : <%=session.getAttribute("usuario") %> </h3>
					
					<p><a href="?accion=logout">Cerrar Sesion</a></p><!-- Se procesa en doget -->
					<p><a href="?accion=registroAdmin">Registrar Administrador</a></p>
					<p><a href="?accion=consultarAdministradores">Consultar Administradores</a></p>
					<p><a href="?accion=registroPregunta">Registrar pregunta</a></p>
					<p><a href="?accion=envioCorreo">Enviar Correo - Electronico</a></p>													
					<p>Contenido Principal</p>
					
				</div>
			</div>
		</div>
	</div>
</div>
	
</body>
</html>