package model;

import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;  
import org.apache.commons.mail.SimpleEmail;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import webservice.CupoService;
import webservice.QueueService;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;

public class TestScheduler extends TimerTask {
	private String name;

	public TestScheduler(String n) {
		this.name = n;
	}

	public String returnName() {
		return this.name;
	}

	public boolean schedule(JSONObject jsonObject) {
		System.out.println(jsonObject.get("body"));
		CupoService cupoService = new CupoService();
		// Obtengo los cupos existentes.
		String jsonCupos = this.getCupos(cupoService);
		// reservo los cupos
		JSONArray jsonCuposArray = new JSONArray(jsonCupos);
		if (!jsonCuposArray.isNull(0) && !jsonCuposArray.isEmpty()) {
			JSONObject jsonObjectCupo = new JSONObject(jsonCuposArray.get(0).toString());
			String codigoReserva = jsonObjectCupo.getString("codigo_reserva");
			JSONObject jsonMessageBody = new JSONObject(jsonObject.getString("body"));
			String cedula = String.valueOf(jsonMessageBody.getInt("cedula"));
			String setCupo = cupoService.reservarCupo(cedula, codigoReserva);
			// Elimino los mensajes de la cola si todo ocurre correcto.
			SqsClient sqsClient = SqsClient.builder().region(Region.US_WEST_1).build();
			this.deleteMessage(jsonObject.getString("receiptHandle"), jsonObject, sqsClient);
			this.sendMail(jsonMessageBody,jsonObjectCupo);
			return true;
		}
		return false;

	}

	public void sendMail(JSONObject usuarioFinal,JSONObject cupo) {
		  String to = usuarioFinal.getString("email");

	      // Put sender�s address
	      String from = "agenda.covid.ucudal@gmail.com";
	      final String username = "agenda.covid.ucudal@gmail.com";//username generated by Mailtrap
	      final String password = "AbalAcostaMarquez.024";//password generated by Mailtrap

	      // Paste host address from the SMTP settings tab in your Mailtrap Inbox
	      String host = "smtp.gmail.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");//it�s optional in Mailtrap
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "25");// use one of the options in the SMTP settings tab in your Mailtrap Inbox
	      props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
	    }
	         });

	      try {
	    // Create a default MimeMessage object.
	    Message message = new MimeMessage(session);

	    // Set From: header field
	    message.setFrom(new InternetAddress(from));

	    // Set To: header field
	    message.setRecipients(Message.RecipientType.TO,
	               InternetAddress.parse(to));

	    // Set Subject: header field
	    message.setSubject("Agenda COVID-19.");

	    // Put the content of your message
	    message.setText("Primera Dosis: " + cupo.getString("fec_primer_dosis") + "\n Lugar: "
	    		+ cupo.getString("lugar") + "\n"
	    		+"Localidad: " + cupo.getString("localidad") + "\n\n"
	    		+ "Segunda Dosis: " + cupo.getString("fec_segunda_dosis") + "\n Lugar: "
	    		+ cupo.getString("lugar") + "\n"
	    		+"Localidad: " + cupo.getString("localidad") + "\n");

	    // Send message
	    Transport.send(message);

	    System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
	   }
	    
	
	public void deleteMessage(String recieptHandle, JSONObject jsonObject, SqsClient sqsClient) {
		String queueUrl = "https://sqs.us-west-1.amazonaws.com/457393350873/queue-qa.fifo";
		DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder().queueUrl(queueUrl)
				.receiptHandle(recieptHandle).build();
		sqsClient.deleteMessage(deleteMessageRequest);
	}

	public String getCupos(CupoService cupoService) {
		String jsonCupos = cupoService.cupo();
		return jsonCupos;
	}

	@Override
	public void run() {
		// Creo una queue service de las que est�n en los webServices
		QueueService qs = new QueueService();
		// Obtengo el json de la cola SQS de aws.
		String jsonString = qs.getQueue();
		// Convierto a un array JSON (Strings) lo obtenido
		JSONArray jsonArray = new JSONArray(jsonString);
		// Si el array no es vac�o trabajo con el como JSONObjects
		if (!jsonArray.isNull(0) && !jsonArray.isEmpty()) {
			for (Object jsons : jsonArray) {
				JSONObject jsonObject = new JSONObject(jsons.toString());
				System.out.println(jsons.toString());
				JSONObject jsonObjectMessage = new JSONObject(jsonObject.getString("body"));
				this.schedule(jsonObject);
				break;
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
