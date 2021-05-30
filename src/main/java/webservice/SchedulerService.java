package webservice;

import java.util.ArrayList;
import java.util.Timer;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

import dto.CupoObject;
import dto.SchedulerObject;
import model.CupoManager;
import model.TestScheduler;

@Path("/WebService")
public class SchedulerService {
	@GET
	@Path("/InitiateScheduler")
	@Produces("application/json")
	public String initiateScheduler() {
		TestScheduler te1=new TestScheduler("Task1");

	    Timer t=new Timer();
	    t.scheduleAtFixedRate(te1, 0,5*1000);
	    Gson gson = new Gson();
	    ArrayList<SchedulerObject> jsonData = new ArrayList<>();
	    SchedulerObject sch = new SchedulerObject();
	    sch.setName(te1.returnName());
	    jsonData.add(sch);
		return gson.toJson(jsonData);
	}
	
}
