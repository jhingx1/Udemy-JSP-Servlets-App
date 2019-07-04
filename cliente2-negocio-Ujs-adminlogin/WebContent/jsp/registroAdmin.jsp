<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1>Registro de Administrador</h1>
<br/>
<form action="?accion=registrarAdmin" method="post">
	<table>
		<tr>
			<td>Correo Electronico</td>
			<td><input type="text" name="email" size="40"/></td>
		</tr>
		<tr>
			<td>Contraeña</td>
			<td><input type="password" name="contrasena" size="40"/></td>
		</tr>
		<tr>
			<td>Repite Contraseña</td>
			<td><input type="password" size="40"/></td>
		</tr>
		<tr>
			<td>Nombre completo</td>
			<td><input type="text" name="nombre" size="40"/></td>
		</tr>
		<tr>
			<td>Elige una pregunta secreta</td>
			<td>			
				<!-- Manejo de excepciones -->		
				<c:catch var="ex">
					<!-- Para compartir la variables de admin para consutar a la tabla pregunta. -->		
					<c:set var="id" value="${admin.id}"/> <!-- Variable para conectarla con tb pregunta -->			
					<sql:query var="rs" dataSource="jdbc/novellius">
						SELECT pregunta from pregunta;
					</sql:query>
					
					<!-- iterar las filas que no debueve las filas -->			
					<c:forEach var="row" items="${rs.rows}"> <!-- nos debuelve todas las filas que esten incluidas en el datasource = rs -->						
						<select name="pregunta">
							<option value="${row.id}">${row.pregunta}</option>
						</select>															
					</c:forEach>
					<br/>
				</c:catch>
				
				<c:if test="${ex != null }">
					<span style="color:red;">
						* Error en la Conexion.
					</span>
				</c:if>
			
			</td>			
		</tr>
		<tr>
			<td>Captura tu respuesta secreta</td>
			<td><input type="text" name="respuesta" size="40"/></td>
		</tr>
		<tr>
			<td>Seleccione una foto</td>
			<td></td>
		</tr>
	</table>
</form>

</body>
</html>