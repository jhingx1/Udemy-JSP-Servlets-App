/**
 * 
 */

function cargarImagen(){
	
	var formdata = new FormData(); //FormData : formulario con codigo
	//para que el navegador abra una carpeta para seleecionar imagenes
	formData.append("file",document.getElementeByID("file").file[0]);
	
	//para realizar una peticion
	var xhr = new XMLHttpRequest();
	//method,contexto->peticion de servidor-servlet,si es asincrona o sincrona
	xhr.open("post","/ServletAjax?accion=cargarImagen","true");
	//envia el formulario a la peticion
	xhr.send(formdata);
	
	xhr.onload = function(){
		//status = variable que guarda el resultado de la peticion:si el server estaba ocupado o otroas
		if(this.status==200){
			alert("peticion exitosa");
		}else{
			alert("Fallo peticion")
		}
	};
	
}