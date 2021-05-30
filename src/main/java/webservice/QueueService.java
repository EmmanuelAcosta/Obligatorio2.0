package webservice;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.gson.Gson;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.SqsClientBuilder;
import software.amazon.awssdk.services.sqs.model.*;
import java.util.List;

@Path("/WebService")
public class QueueService {
	/*@GET
	@Path("/GetQueue")
	@Produces("application/json")
	public String cupo(@QueryParam("cedula") String cedula) {

		SendMessageRequest send_msg_request = new SendMessageRequest()
				.withQueueUrl("https://sqs.us-west-1.amazonaws.com/457393350873/queue-qa.fifo")
				.withMessageBody("hello world").withDelaySeconds(5);
		sqs.sendMessage(send_msg_request);
		return null;
	}*/

	@GET
	@Path("/GetQueue")
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