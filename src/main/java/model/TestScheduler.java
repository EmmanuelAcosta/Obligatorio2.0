package model;

import java.util.Date;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import webservice.CupoService;
import webservice.QueueService;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;

public class TestScheduler extends TimerTask {
	private String name;

	public TestScheduler(String n) {
		this.name = n;
	}
	public String returnName() {
		return this.name;
	}
	public void schedule(JSONObject jsonObject) {
		System.out.println(jsonObject.get("body"));
		CupoService cupoService = new CupoService();
		//Obtengo los cupos existentes.
		String jsonCupos = this.getCupos(cupoService);
		//reservo los cupos
		JSONArray jsonCuposArray = new JSONArray(jsonCupos);
		if(!jsonCuposArray.isNull(0)&&!jsonCuposArray.isEmpty()) {
			JSONObject jsonObjectCupo = new JSONObject(jsonCuposArray.get(0).toString());
			//String setCupo = cupoService.reservarCupo("Deberia ir cedula", "Deberia ir ");
			//Elimino los mensajes de la cola si todo ocurre correcto.
			SqsClient sqsClient = SqsClient.builder().region(Region.US_WEST_1).build();
			//this.deleteMessage(jsonObject.getString("recieptHandle"), jsonObject, sqsClient);
		}
		
	}
	public void deleteMessage(String recieptHandle,JSONObject jsonObject,SqsClient sqsClient) {
		String queueUrl = "https://sqs.us-west-1.amazonaws.com/457393350873/queue-qa.fifo";
		DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(recieptHandle)
                .build();
            sqsClient.deleteMessage(deleteMessageRequest);
	}
	
	public String getCupos(CupoService cupoService) {
		String jsonCupos = cupoService.cupo();
		return jsonCupos;
	}
	@Override
	public void run() {
		//Creo una queue service de las que están en los webServices
		QueueService qs = new QueueService();
		//Obtengo el json de la cola SQS de aws.
		String jsonString = qs.getQueue();
		//Convierto a un array JSON (Strings) lo obtenido
		JSONArray jsonArray = new JSONArray(jsonString);
		//Si el array no es vacío trabajo con el como JSONObjects
		if(!jsonArray.isNull(0) && !jsonArray.isEmpty()) {
			for(Object jsons : jsonArray) {
				JSONObject jsonObject = new JSONObject(jsons.toString());
				this.schedule(jsonObject);
			}
			System.out.println(jsonArray.get(0));
		}
		
		System.out.println(
				Thread.currentThread().getName() + " " + name + " the task has executed successfully " + new Date());
		if ("Task1".equalsIgnoreCase(name)) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
