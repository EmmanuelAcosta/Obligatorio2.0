/**
 * 
 */

function buscarHora(){
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
			respuesta = eval(respuesta)
			alert(respuesta)
		}
	})
}