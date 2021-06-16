
$(document).ready(function(){
	ocultoMensajeError("Cedula");
	ocultoMensajeError("Nombre");
	ocultoMensajeError("Apellido");
	ocultoMensajeError("Email");
	ocultoMensajeError("FechaNacimiento");
	ocultoMensajeError("Telefono");
	ocultoMensajeError("Localidades");
	ocultoMensajeError("Tiempo");
});


function buscarHora(){
	insertarUsuario();
	var usuario = getEstado();
	if(usuario[0].estado==0){
		var jsonCupos = buscarHoraCupo();
		var reserva = reservarCupo(usuario,jsonCupos);
		if(reserva==true){
			alert("Se le informará de su agenda cuando queden cupos disponibles");
		}
	}else{
		alert("Ya tiene una agenda en curso");
	}
}

function insertarUsuario(){
	var endpoint = "http://agendavacunacionweb30-env.eba-vvugpwfd.us-west-1.elasticbeanstalk.com/rest/WebService/InsertUser";
	var cedula = $("#Cedula").val();
	var nombre = $("#Nombre").val();
	var apellido = $("#Apellido").val();
	var telefono = $("#Telefono").val();
	var email = $("#Email").val();
	var fecnac = $("#FechaNacimiento").val();
	//var cedula = usuario[0].cedula;
	//var codigo_reserva = jsonCupos[0].codigo_reserva;
	var reserva = new Object();
	var endpoint = "http://agendavacunacionweb30-env.eba-vvugpwfd.us-west-1.elasticbeanstalk.com/rest/WebService/InsertUser";
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
				alert("No se pudo reservar el cupo");
		},
		success: function(respuesta){
				respuesta=eval(respuesta);
				reserva = respuesta;
		}
	})
	return reserva;
		
}
function buscarHoraCupo(){
	//Deberia agregarse archivo de configuración con el enpoint
	var cadena = "http://agendavacunacionweb30-env.eba-vvugpwfd.us-west-1.elasticbeanstalk.com/rest/WebService/GetCupos";
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
	var endpoint = "http://agendavacunacionweb30-env.eba-vvugpwfd.us-west-1.elasticbeanstalk.com/rest/WebService/GetEstado?cedula=" + cedula;
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
//Aca ya no se debe ir mas a reservar cupos se debería insertar en la tabla
//Usuario al usuario en cuestión
//Y debería mandarse directamente a la cola con el sendRequest, quizás modificando
//un poco ese metodo para que haga la inserción si no existe, y si no solamente lo envía
//a la cola.
function reservarCupo(usuario,jsonCupos){
	var cedula = $("#Cedula").val();
	var nombre = $("#Nombre").val();
	var apellido = $("#Apellido").val();
	var telefono = $("#Telefono").val();
	var email = $("#Email").val();
	var fecnac = $("#FechaNacimiento").val();
	var reserva = new Object();
	var endpoint = "http://agendavacunacionweb30-env.eba-vvugpwfd.us-west-1.elasticbeanstalk.com/rest/WebService/PostQueue";
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
				alert("No se pudo reservar el cupo");
		},
		success: function(respuesta){
				respuesta=eval(respuesta);
				reserva = respuesta;
		}
	})
	return reserva;
}

function iniciarScheduler(){
	var endpoint = "http://agendavacunacionweb30-env.eba-vvugpwfd.us-west-1.elasticbeanstalk.com/rest/WebService/InitiateScheduler";
	$.ajax({
		url:endpoint,
		type: 'GET',
		dataType: 'json',
		data:{},
		async:false,
		error: function(){
				alert("No anda na");
		},
		success: function(respuesta){
				respuesta=eval(respuesta);
		}
	})
}

