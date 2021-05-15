package webservice;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

import dto.CupoObject;
import model.CupoManager;			
@Path("/WebService")
public class CupoService {
	@GET
	@Path("/GetCupos")
	@Produces("application/json")
	public String cupo() {
		String cupos = null;
		try {
			ArrayList<CupoObject> cupoData = null;
			CupoManager cupoManager = new CupoManager();
			cupoData = cupoManager.getCupos();
			Gson gson = new Gson();
			System.out.println(gson.toJson(cupoData));
			cupos = gson.toJson(cupoData);
		}

		catch (Exception e) {
			System.out.println("Exception Error"); // Console
		}
		return cupos;
	}
}
