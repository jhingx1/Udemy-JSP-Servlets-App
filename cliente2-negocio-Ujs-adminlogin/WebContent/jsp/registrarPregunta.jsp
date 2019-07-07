<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Insertar Pregunta Secreta</h1>
	<br />
	
	<c:set var="str" value="${param.pregunta}" /> <!-- param : es un objeto implicito -->
	La logitud de la pregunra es : ${fn:length(str)} <!-- Funciones en jstl -->

	<br/>
	<c:catch var="ex">

		<!-- param es un objeto implicito -->
		<!-- row:numero de filas afectadas -->
		<sql:update var="row" dataSource="jdbc/novellius"
			sql="insert into pregunta (pregunta) values (?)">
			<sql:param value="${param.pregunta}"></sql:param>
		</sql:update>

		<c:choose>

			<c:when test="${row != 0}">Pregunta registrada correctamente<br />
			</c:when>
			<c:otherwise>Error al registrar la pregunta <br />
			</c:otherwise>

		</c:choose>


	</c:catch>

	<c:if test="${ex != null }">
		<span style="color: red;"> * Error en la Conexion. </span>
	</c:if>

</body>
</html>