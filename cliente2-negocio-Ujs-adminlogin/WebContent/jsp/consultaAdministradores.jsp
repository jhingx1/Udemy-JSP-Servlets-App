<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<script type="text/javascript"
	src='<c:url value="/js/jquery-3.4.1.js" />'></script>
<script type="text/javascript"
	src='<c:url value="/js/bootstrap.min.js" />'></script>
<link rel="stylesheet"
	href="<c:url value='/css/bootstrap-theme.min.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css'/>"
	type="text/css" />  
<!-- 
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  
  -->

<title>Consulta Administradores</title>
</head>
<body>
	
	<br />
	
	<!-- <c:out value="${requestScope.mensaje}" /> -->
	<br />
	<div class="container">			
		
		
		<div class="panel panel-primary">
		    <div class="panel-heading">Consulta Administradores</div>
		    <div class="panel-body">
		    	<table class="table table-bordered table-striped">
					<thead>
						<tr>
							<th>Email</th>
							<th>Nombre Completo</th>
							<th>Pregunta Secreta</th>
							<th>Respuesta Secreta</th>
							<th>Foto</th>
						</tr>
					</thead>
						<!-- Iteramos el arrayList -->
						<c:forEach var="admin" items="${sessionScope.administradores}">
						<tbody>
							<tr>
								<td>${admin.email}</td>
								<td>${admin.nombre}</td>					
								<td>
								<!-- Manejo de excepciones --> <c:catch var="ex">
									<!-- Para compartir la variables de admin para consutar a la tabla pregunta. -->
									<c:set var="id" value="${admin.id}" />
									<!-- Variable para conectarla con tb pregunta -->
									<sql:query var="rs" dataSource="jdbc/novellius">
										SELECT id,pregunta from pregunta where id = id;
									</sql:query>
			
									<!-- iterar las filas que no debueve las filas -->
									<c:forEach var="row" items="${rs.rows}">
										<!-- nos debuelve todas las filas que esten incluidas en el datasource = rs -->
										<c:if test="${admin.id == row.id}">
											<c:out value="${row.pregunta}" />
										</c:if>
										<!-- <c:out value="${row.pregunta}"/> -->
									</c:forEach>
									<br />
								</c:catch> <c:if test="${ex != null }">
									<span style="color: red;"> * Error en la Conexion. </span>
								</c:if>
								</td>
								<td>${admin.respuesta}</td>
								<td><img src="<c:url value='${admin.urlImagen}'/>"></td>
							</tr>
							
						</tbody>
					</c:forEach>
				</table>
		    
		    </div>
		</div>			
		
	</div>

</body>
</html>