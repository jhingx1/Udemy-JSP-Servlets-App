/**
 * 
 */

function cargarImagen(){
	
	var formData = new FormData(); //FormData : formulario con codigo
	//para que el navegador abra una carpeta para seleecionar imagenes
	formData.append("file",document.getElementById("file").files[0]);
	
	//para realizar una peticion
	var xhr = new XMLHttpRequest();
	//method,contexto->peticion de servidor-servlet,si es asincrona o sincrona
	//Este es una peticion con el paso del contexto. Si que remos borrar el contexto
	//debemos ir a  servidor y borrarlo y que da asi : /ServletAjax?accion=cargarImagen
	xhr.open("post","/cliente2-negocio-Ujs-adminlogin/ServletAjax?accion=cargarImagen","true");
	//envia el formulario a la peticion
	xhr.send(formData);
	
	xhr.onload = function(){
		//status = variable que guarda el resultado de la peticion:si el server estaba ocupado o otroas
		if(this.status==200){
			alert("peticion exitosa");
		}else{
			alert("Fallo peticion")
		}
	};
	
}