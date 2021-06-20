package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.JSONArray;
import org.json.JSONObject;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import webservice.CupoService;
import webservice.QueueService;

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
			this.sendMail(jsonMessageBody, jsonObjectCupo);
			return true;
		}
		return false;

	}

	public void sendMail(JSONObject usuarioFinal, JSONObject cupo) {
		String recipient = usuarioFinal.getString("email");
		String sender = "agenda.covid.ucudal@gmail.com";
		// String FROMNAME = "Ministerio de salud publica";

		// Region
		Region region = Region.US_WEST_1;
		SesClient client = SesClient.builder().region(region).build();
		String bodyText = "Primera Dosis: " + cupo.getString("fec_primer_dosis") + "\n Lugar: "
				+ cupo.getString("lugar") + "\n" + "Localidad: " + cupo.getString("localidad") + "\n\n"
				+ "Segunda Dosis: " + cupo.getString("fec_segunda_dosis") + "\n Lugar: " + cupo.getString("lugar")
				+ "\n" + "Localidad: " + cupo.getString("localidad") + "\n";
		String bodyHTML = "<html>" + "<head></head>" + "<body>" + "<h1>Horarios de vacunacion!</h1>" + "<p>"
				+ "Primera Dosis: " + cupo.getString("fec_primer_dosis") + "</p>" + "<p>" + "Lugar: "
				+ cupo.getString("lugar") + "</p>" + "<p>" + "Localidad: " + cupo.getString("localidad") + "</p>"
				+ "<br>" + "<p>" + "Segunda Dosis: " + cupo.getString("fec_segunda_dosis") + "</p>" + "<p>" + "Lugar: "
				+ cupo.getString("lugar") + "</p>" + "<p>" + "Localidad: " + cupo.getString("localidad") + "</p>"
				+ "</body>" + "</html>";
		String subject = "Agenda COVID-19.";
		try {
			send(client, sender, recipient, subject, bodyText, bodyHTML);
			client.close();
			System.out.println("Done");

		} catch (IOException | MessagingException e) {
			e.getStackTrace();
		}

	}

	public static void send(SesClient client, String sender, String recipient, String subject, String bodyText,
			String bodyHTML) throws AddressException, MessagingException, IOException {

		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage message = new MimeMessage(session);

// Add subject, from and to lines
		message.setSubject(subject, "UTF-8");
		message.setFrom(new InternetAddress(sender));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

// Create a multipart/alternative child container
		MimeMultipart msgBody = new MimeMultipart("alternative");

// Create a wrapper for the HTML and text parts
		MimeBodyPart wrap = new MimeBodyPart();

// Define the text part
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(bodyText, "text/plain; charset=UTF-8");

// Define the HTML part
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(bodyHTML, "text/html; charset=UTF-8");

// Add the text and HTML parts to the child container
		msgBody.addBodyPart(textPart);
		msgBody.addBodyPart(htmlPart);

// Add the child container to the wrapper object
		wrap.setContent(msgBody);

// Create a multipart/mixed parent container
		MimeMultipart msg = new MimeMultipart("mixed");

// Add the parent container to the message
		message.setContent(msg);

// Add the multipart/alternative part to the message
		msg.addBodyPart(wrap);

		try {
			System.out.println("Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			message.writeTo(outputStream);
			ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

			byte[] arr = new byte[buf.remaining()];
			buf.get(arr);

			SdkBytes data = SdkBytes.fromByteArray(arr);
			RawMessage rawMessage = RawMessage.builder().data(data).build();

			SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder().rawMessage(rawMessage).build();

			client.sendRawEmail(rawEmailRequest);

		} catch (SesException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(1);
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
		// Creo una queue service de las que están en los webServices
		QueueService qs = new QueueService();
		// Obtengo el json de la cola SQS de aws.
		String jsonString = qs.getQueue();
		// Convierto a un array JSON (Strings) lo obtenido
		JSONArray jsonArray = new JSONArray(jsonString);
		// Si el array no es vacío trabajo con el como JSONObjects
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
