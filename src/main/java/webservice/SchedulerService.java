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
import scheduler.SchedulerAvisoContagio;
import scheduler.SchedulerCalculoDias;


@Path("/WebService")
public class SchedulerService {
	@GET
	@Path("/InitiateScheduler")
	@Produces("application/json")
	public String initiateScheduler() {
		TestScheduler te1=new TestScheduler("Task1");

	    Timer t=new Timer();
	    t.scheduleAtFixedRate(te1, 0,3600*1000);
	    Gson gson = new Gson();
	    ArrayList<SchedulerObject> jsonData = new ArrayList<>();
	    SchedulerObject sch = new SchedulerObject();
	    sch.setName(te1.returnName());
	    jsonData.add(sch);
		return gson.toJson(jsonData);
	}
	@GET
	@Path("/InitiateSchedulerCalculoDias")
	@Produces("application/json")
	public String initiateSchedulerCalculoDias() {
		SchedulerCalculoDias te1=new SchedulerCalculoDias("Task2");

	    Timer t=new Timer();
	    t.scheduleAtFixedRate(te1, 0,7200*1000);
	    Gson gson = new Gson();
	    ArrayList<SchedulerObject> jsonData = new ArrayList<>();
	    SchedulerObject sch = new SchedulerObject();
	    sch.setName(te1.returnName());
	    jsonData.add(sch);
		return gson.toJson(jsonData);
	}
	
	@GET
	@Path("/InitiateSchedulerPositivo")
	@Produces("application/json")
	public String initiateSchedulerPositivo() {
		SchedulerAvisoContagio te1=new SchedulerAvisoContagio("Task3");

	    Timer t=new Timer();
	    t.scheduleAtFixedRate(te1, 0,7200*1000);
	    Gson gson = new Gson();
	    ArrayList<SchedulerObject> jsonData = new ArrayList<>();
	    SchedulerObject sch = new SchedulerObject();
	    sch.setName(te1.returnName());
	    jsonData.add(sch);
		return gson.toJson(jsonData);
	}
	
}
