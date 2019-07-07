<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- <script src="js/lib/jquery-2.1.4.min.js"></script> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<title>Login-Portal</title>
</head>
<body>

<div class="container">
	<div class="row">
		
		<div class="col-md-6 col-md-offset-3">
		
			<!-- Para mostrar el mensaje de error -->
			<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-6">
								<a href="#" class="active" id="login-form-link">Login - Demos Branches</a>								
							</div>
							<div class="col-xs-6">
								<a href="#" class="active" id="login-form-link">Registrar</a>								
							</div>
												
						</div>
							
					</div>
			</div>
		
			<form method="post" action="?accion=iniciarSession">
				
				
				<%--  <%=session.getAttribute("usuario") %> Para ver si la sesscion se ha destruido--%>							
				<span style="color:red;font-weigh:bold;">
						
			<!-- Para mostra el error con scope --> 
<%-- 		<c:if test="${requestScope.error != null}" > requestScope : objetoImplicito --%>
<%-- 			<c:out value="${requestScope.error}" /> --%>
<%-- 		</c:if> --%>
				<!-- Como Switch  -->
				<c:choose> 
					<c:when test="${requestScope.error != null}"> <!-- Como CASE -->
						<c:out value="${requestScope.error}" />
					</c:when>
					<c:otherwise><!-- else - default -->
						Sin Errores
					</c:otherwise>
				</c:choose>
			
					<br />
				
				<%-- Uso de Scripts - no recomendado<%
				String error ="Script " + (String) request.getAttribute("error");
				if(error != null){
					out.print(error);
				}							
				%> --%>
				
				</span>				
				
				<div class = "form-group">
					<label>Nombre de Usuario</label>
					<%
						Cookie[] cookies = request.getCookies();
						String valor = "";
						for(Cookie c:cookies){
							if(c.getName().equals("usuario")){
								valor = c.getValue();  //getValue es una propiedad de la cadena										
							}else{
								out.println("No encontrado");
							}
						}
					
					%>
					<input type="text" name="usuario" class="form-control" value="<%=valor%>"/>
				</div>
				<div class = "form-group">
					<label>Contraseña</label>
					<input type="password" name="contrasena" class="form-control" />
				</div>
				<div class = "checkbox">
					<label><input type="checkbox"  name="ckbox"/>Recuerda mis Datos</label>			
				</div>
				
				<div class="row">
					<div class="col-sm-8 col-sm-offset-5">
						<!-- <input type="hidden" name="accion" value="iniciarSession"/> -->
						<button type="submit" class="btn btn-success">Iniciar Sesion</button>
					</div>
				</div>				
			</form>
			
			<!-- <a href="?accion=inicio">Regresar</a>  -->
					
		</div>
	</div>
</div>


	<!-- <h1>Login.jsp</h1>

	<form method="post" action="?accion=iniciarSesion"><form method="get">
		<table>
  			<tr> Filas
  				<td>Nombre Usuario</td> Columnas
  				<td><input type="text" name="usuario"/></td>
  			</tr>
  			<tr>
  				<td>Contraseña</td>
  				<td><input type="password" name="contrasena"/></td>
  			</tr>
  			<tr>
  				<td></td>
  				<td><input type="checkbox" checked="checked" value="Recordar mi Datos"/></td>
  			</tr>
  			<tr>
  				<td><input type="hidden" name="accion" value="iniciarSesion"/></td>
  				<td><input type="submit" value="Iniciar Sesion"/></td>
  			</tr>
  			
		</table>
	</form> -->
	
</body>
</html>