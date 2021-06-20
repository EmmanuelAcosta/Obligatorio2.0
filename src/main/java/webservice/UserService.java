package webservice;

import java.sql.Date;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.tomcat.jni.User;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;

import dao.Usuario;
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
	@POST
	@Path("/InsertUser")
	@Produces("application/json")
	@Consumes("application/json")
	public String insertUser(@RequestBody String userStr) {
		JSONObject jsonObject  = new JSONObject(userStr); 
		JSONObject jsonUser = jsonObject.getJSONObject("user");
		String usuario = null;
		UserObject user = new UserObject();
		user.setNombre(jsonUser.getString("nombre"));
		user.setApellido(jsonUser.getString("apellido"));
		user.setCedula(jsonUser.getString("cedula"));
		Date date = Date.valueOf(jsonUser.getString("fecnac"));
		user.setFecha_nacimiento(date);
		user.setEmail(jsonUser.getString("email"));
		user.setTelefono(jsonUser.getString("telefono"));
		try {
			
			UserManager userManager = new UserManager();
			boolean userInsert = userManager.insertUser(user);
			Gson gson = new Gson();
			usuario = gson.toJson(userInsert);
		}

		catch (Exception e) {
			System.out.println("Exception Error"); // Console
		}
		return usuario;
	}
}
