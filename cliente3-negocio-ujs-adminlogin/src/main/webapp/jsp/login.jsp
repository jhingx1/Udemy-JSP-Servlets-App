<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<div class="container">
	<div class="row">	
		<form method="get">							
				<div class = "form-group">
					<label>Nombre de Usuario</label>					
					<input type="text" name="usuario" class="form-control"/>
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
						<input type="hidden" name="accion" value="iniciarSession"/>
						<button type="submit" class="btn btn-success">Iniciar Sesion</button>
					</div>
				</div>				
			</form>
	
	</div>
</div>	

	<a href="?accion=inicio">Regresar</a>
</body>
</html>