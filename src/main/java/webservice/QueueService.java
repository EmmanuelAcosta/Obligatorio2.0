package webservice;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.gson.Gson;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

@Path("/WebService")
public class QueueService {
	@GET
	@Path("/GetQueue")
	@Produces("application/json")
	public String cupo(@QueryParam("cedula") String cedula) {
		
		SendMessageRequest send_msg_request = new SendMessageRequest()
		        .withQueueUrl("https://sqs.us-west-1.amazonaws.com/457393350873/queue-qa.fifo")
		        .withMessageBody("hello world")
		        .withDelaySeconds(5);
		sqs.sendMessage(send_msg_request);
		return usuario;
	}
}