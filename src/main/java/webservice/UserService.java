package webservice;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.gson.Gson;

import dto.CupoObject;
import dto.UserObject;
import model.CupoManager;
import model.UserManager;

@Path("/WebService")
public class UserService {
	@GET
	@Path("/GetEstado")
	@Produces("application/json")
	public String cupo(@QueryParam("cedula") String cedula) {
		
		String usuario = null;
		try {
			ArrayList<UserObject> userData = null;
			UserManager userManager = new UserManager();
			userData = userManager.getEstado(cedula);
			Gson gson = new Gson();
			usuario = gson.toJson(userData);
		}

		catch (Exception e) {
			System.out.println("Exception Error"); // Console
		}
		return usuario;
	}
}
