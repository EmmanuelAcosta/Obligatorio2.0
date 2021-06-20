package webservice;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.gson.Gson;

import dto.ContagioObject;
import model.ContagioManager;

@Path("/WebService")
public class ContagioService {
	@POST
	@Path("/InsertContagio")
	@Produces("application/json")
	public String insertUser(@QueryParam("cedula_principal") String cedulaPrincipal,@QueryParam("cedula_contacto") String cedulaContacto) {
		 
		//JSONObject jsonUser = jsonObject.getJSONObject("user");
		String contagio= null;
		ContagioObject contObj = new ContagioObject();
		contObj.setCedula_principal(cedulaPrincipal);
		contObj.setCedula_contacto(cedulaContacto);
		try {
			
			ContagioManager contManager = new ContagioManager();
			boolean contInsert = contManager.insertUser(contObj);
			Gson gson = new Gson();
			contagio = gson.toJson(contInsert);
		}

		catch (Exception e) {
			System.out.println("Exception Error"); // Console
		}
		return contagio;
	}
	
	@POST
	@Path("/InsertPositivo")
	@Produces("application/json")
	public String insertPositivo(@QueryParam("cedula_principal") String cedulaPrincipal) {
		 
		//JSONObject jsonUser = jsonObject.getJSONObject("user");
		String contagio= null;
		ContagioObject contObj = new ContagioObject();
		contObj.setCedula_principal(cedulaPrincipal);
		try {
			
			ContagioManager contManager = new ContagioManager();
			boolean contInsert = contManager.insertPositivo(contObj);
			Gson gson = new Gson();
			contagio = gson.toJson(contInsert);
		}

		catch (Exception e) {
			System.out.println("Exception Error"); // Console
		}
		return contagio;
	}
}
