<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- cliente2-negocio-Ujs-adminlogin : contexto de mi aplicacion -->
<!-- Importar resurso estatico -->
<!-- url:/cliente2-negocio-Ujs-adminlogin/js/ajax.js -->
<script type="text/javascript" src='<c:url value="/js/ajax.js" />'></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registro Admininistrador</title>

</head>
<body>

	<h1>Registro de Administrador</h1>
	<br />
	
	<c:out value="${requestScope.msg}"/> <br/>
	
	<form action="?accion=registrarAdmin" method="post">
		<table>
			<tr>
				<td>Correo Electronico</td>
				<td><input type="text" name="email" size="40" /></td>
			</tr>
			<tr>
				<td>Contraeña</td>
				<td><input type="password" name="contrasena" size="40" /></td>
			</tr>
			<tr>
				<td>Repite Contraseña</td>
				<td><input type="password" size="40" /></td>
			</tr>
			<tr>
				<td>Nombre completo</td>
				<td><input type="text" name="nombre" size="40" /></td>
			</tr>
			<tr>
				<td>Elige una pregunta secreta</td>
				<td>
					<!-- Manejo de excepciones --> <c:catch var="ex">
						<!-- Para compartir la variables de admin para consutar a la tabla pregunta. -->
						<c:set var="id" value="${admin.id}" />
						<!-- Variable para conectarla con tb pregunta -->
						<sql:query var="rs" dataSource="jdbc/novellius">
						SELECT * from pregunta;
					</sql:query>

						<!-- iterar las filas que no debueve las filas -->
						<select name="pregunta">
							<c:forEach var="row" items="${rs.rows}">
								<!-- nos debuelve todas las filas que esten incluidas en el datasource = rs -->
								<option value="${row.id}">${row.pregunta}</option>
							</c:forEach>
						</select>
						<br />
					</c:catch> <c:if test="${ex != null }">
						<span style="color: red;"> * Error en la Conexion. </span>
					</c:if>

				</td>
			</tr>
			<tr>
				<td>Captura tu respuesta secreta</td>
				<td><input type="text" name="respuesta" size="40" /></td>
			</tr>
			<tr>
				<td>Seleccione una foto</td>
				<td><input type="file" id="file" /><input type="button" value="cargar" onclick="cargarImagen()"/>
					<br/><div id="respuesta" style="font-weight:bold"></div>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="Crear"/></td>
				<td></td>
			</tr>
		</table>
	</form>

</body>
</html>