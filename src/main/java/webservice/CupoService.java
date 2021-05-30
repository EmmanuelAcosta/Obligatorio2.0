package webservice;

import java.util.ArrayList;
import java.util.Timer;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.gson.Gson;

import dto.CupoObject;
import model.CupoManager;
import model.TestScheduler;			
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
	
	@POST
	@Path("/SetCupo")
	@Produces("application/json")
	public String reservarCupo(@QueryParam("cedula") String cedula,@QueryParam("codigo_reserva") String codigo_reserva) {
		String cupos = null;
		try {
			boolean databaseState = false;
			CupoManager cupoManager = new CupoManager();
			databaseState = cupoManager.setCupos(cedula,codigo_reserva);
			Gson gson = new Gson();
			cupos = gson.toJson(databaseState);
		}

		catch (Exception e) {
			System.out.println("Exception Error"); // Console
		}
		return cupos;
	}
}
