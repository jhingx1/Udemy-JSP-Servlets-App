<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
	String s = "Este mensaje no es muy dinamico";
	out.println(s);
%>
<p>Este mensaje no es muy dinamico</p>

<%
	out.println("Esta contenido es dinamicos : <hr/>"+request.getHeader("USER-AGENT"));
%>

</body>
</html>