
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
	if(usuario[0].estado==0){
		var jsonCupos = buscarHoraCupo();
		var reserva = reservarCupo(usuario,jsonCupos);
		if(reserva==true){
			alert("Se encuentra agendado- Día 1er dosis: " + jsonCupos[0].fec_primer_dosis + "- Dia 2da dosis: " + jsonCupos[0].fec_segunda_dosis);
		}
	}else{
		alert("Ya tiene una agenda en curso");
	}
}
function buscarHoraCupo(){
	//Deberia agregarse archivo de configuración con el enpoint
	var cadena = "http://localhost:8089/HelloREST/rest/WebService/GetCupos";
	//var cedula = $("#Cedula").val();
	var cupos = new Object();
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
			cupos = respuesta;
		}
	})
	return cupos;
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
function MuestroMensajError(idCampo){
    $('#' +"error_" + idCampo).fadeIn(800);
}

function reservarCupo(usuario,jsonCupos){
	var cedula = usuario[0].cedula;
	var codigo_reserva = jsonCupos[0].codigo_reserva;
	var reserva = new Object();
	var endpoint = "http://localhost:8089/HelloREST/rest/WebService/SetCupo?cedula=" + cedula +"&codigo_reserva="+codigo_reserva;
	$.ajax({
		url:endpoint,
		type: 'POST',
		dataType: 'json',
		data:{},
		async:false,
		error: function(){
				alert("No se pudo reservar el cupo");
		},
		success: function(respuesta){
				respuesta=eval(respuesta);
				reserva = respuesta;
		}
	})
	return reserva;
}

