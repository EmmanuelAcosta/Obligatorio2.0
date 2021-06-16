/**
 * Vamos a tener unas funciones por acá, las quiero separar del funciones.js
 */

function enviarContacto(){
	var cedulaPrincipal = $("#cedula_principal").val();
	var cedulaContacto = $("#cedula_contacto").val();
	
	var insercion = new Object();
	var endpoint = "http://localhost:8089/HelloREST/rest/WebService/InsertContagio?cedula_principal=" + cedulaPrincipal + "&cedula_contacto=" + cedulaContacto;
	$.ajax({
		url:endpoint,
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data:{},
		async:false,
		error: function(){
				alert("No se pudo ingresar el contacto");
		},
		success: function(respuesta){
				respuesta=eval(respuesta);
				insercion = respuesta;
		}
	})
	return insercion;

}