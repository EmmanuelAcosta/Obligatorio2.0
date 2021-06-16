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

function enviarPositivo(){
	var cedula = $("#cedula_principal").val();
	
	var insercion = new Object();
	var endpoint = "http://localhost:8089/HelloREST/rest/WebService/InsertPositivo?cedula_principal=" + cedula;
	$.ajax({
		url:endpoint,
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data:{},
		async:false,
		error: function(){
				alert("No se pudo ingresar el positivo");
		},
		success: function(respuesta){
				respuesta=eval(respuesta);
				insercion = respuesta;
				if(!insercion){
					popUp("http://localhost:8089/HelloREST/registro.html");
				}
		}
	})
	return insercion;

}
function popUp(URL) {
        var popUp = window.open(URL, 'Registro en el sistema', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=700,left = 390,top = 50');
        if (popUp == null || typeof(popUp)=='undefined') { 	
			alert('Please disable your pop-up blocker and click the "Open" link again.'); 
		} 
}

function registrarse(){
	var endpoint = "http://localhost:8089/HelloREST/rest/WebService/InsertUser";
	var cedula = $("#Cedula").val();
	var nombre = $("#Nombre").val();
	var apellido = $("#Apellido").val();
	var telefono = $("#Telefono").val();
	var email = $("#Email").val();
	var fecnac = $("#FechaNacimiento").val();
	var reserva = new Object();
	$.ajax({
		url:endpoint,
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data:JSON.stringify({
    		"user": {
        		"cedula": cedula,
        		"nombre": nombre,
        		"apellido": apellido,
        		"telefono":telefono,
        		"fecnac":fecnac,
        		"email":email
    		}
		}),
		async:false,
		error: function(){
				alert("No se pudo registrar al usuario");
		},
		success: function(respuesta){
				respuesta=eval(respuesta);
				reserva = respuesta;
		}
	})
	return reserva;
		
}