
$(document).ready(function(){
	ocultoMensajeError("Cedula");
	ocultoMensajeError("Nombre");
	ocultoMensajeError("Apellido");
	ocultoMensajeError("Email");
	ocultoMensajeError("FechaNacimiento");
	ocultoMensajeError("Telefono");
	ocultoMensajeError("Localidades");
});
function buscarHora(){
	var usuario = getEstado();
	if(usuario["estado"]==0){
		buscarHoraCupo();
	}else{
		alert("Ya tiene una agenda en curso");
	}
}
function buscarHoraCupo(){
	//Deberia agregarse archivo de configuración con el enpoint
	var cadena = "http://localhost:8089/HelloREST/rest/WebService/GetCupos";
	//var cedula = $("#Cedula").val();
	$.ajax({
		url: cadena,
		type: 'GET',
		dataType: 'json',
		data:{},
		timeout: 10000,
		async: false,
		error: function(){
			alert("No se pudo procesar la solicitud")
		},
		success: function(respuesta){
			respuesta = eval(respuesta);
			var obj = JSON.stringify(respuesta);
			alert(obj);
		}
	})
}

function getEstado(){
	var usuario = new Object();
	var cedula = $("#Cedula").val();
	var endpoint = "http://localhost:8089/HelloREST/rest/WebService/GetEstado?cedula=" + cedula;
	$.ajax({
		url: endpoint,
		type: 'GET',
		dataType: 'json',
		data:{},
		timeout: 10000,
		async: false,
		error: function(){
			alert("No se pudo procesar la solicitud")
		},
		success: function(respuesta){
			respuesta = eval(respuesta);
			usuario = respuesta;
		}
	})
	return usuario;
	 
}

function ocultoMensajeError(idCampo){
	$('#' + "error_" + idCampo).fadeOut(400);
}

