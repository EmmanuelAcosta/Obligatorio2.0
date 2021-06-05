package webservice;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.json.JSONObject;

import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;

import dto.UserDTORequest;
import dto.UserObject;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.SqsClientBuilder;
import software.amazon.awssdk.services.sqs.model.*;
import java.util.List;

@Path("/WebService")
public class QueueService {

	@GET
	@Path("/Queue")
	@Produces("application/json")
	public String getQueue() {
		SqsClient sqsClient = SqsClient.builder().region(Region.US_WEST_1).build();
		try {
			String queueUrl = "https://sqs.us-west-1.amazonaws.com/457393350873/queue-qa.fifo";
			ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl)
					.maxNumberOfMessages(5).build();
			List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
			String json = new Gson().toJson(messages);
			//this.deleteMessages(sqsClient, queueUrl, messages);
			return json;
		} catch (SqsException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(1);
		}
		return null;

	}
	@POST
	@Path("/PostQueue")
	@Produces("application/json")
	@Consumes("application/json")
	public boolean setQueue(@RequestBody String user) {
		JSONObject jsonObject  = new JSONObject(user); 
		JSONObject jsonUser = jsonObject.getJSONObject("user");
		System.out.println(jsonUser.toString());
		try {
			String queueUrl = "https://sqs.us-west-1.amazonaws.com/457393350873/queue-qa.fifo";
			SqsClient sqsClient = SqsClient.builder().region(Region.US_WEST_1).build();
			sqsClient.sendMessage(SendMessageRequest.builder()
	                .queueUrl(queueUrl)
	                .messageBody(jsonUser.toString())
	                .messageGroupId("a")
	                //.delaySeconds(10)
	                .build());
			return true;
		} catch (SqsException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(1);
		}
		return false;
	}
		
	public void deleteMessages(SqsClient sqsClient, String queueUrl,  List<Message> messages) {
        System.out.println("\nDelete Messages");
        // snippet-start:[sqs.java2.sqs_example.delete_message]

        try {
            for (Message message : messages) {
                DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();
                sqsClient.deleteMessage(deleteMessageRequest);
            }
            // snippet-end:[sqs.java2.sqs_example.delete_message]

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
   }

}