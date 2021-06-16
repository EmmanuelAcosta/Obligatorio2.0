package scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dao.Database;

public class SchedulerAvisoContagio extends TimerTask {
	private String name;

	public SchedulerAvisoContagio(String n) {
		this.name = n;
	}

	public String returnName() {
		return this.name;
	}

	@Override
	public void run() {
		Database database = new Database();
		Connection connection = database.Get_Connection();
		try {
			
			ArrayList<String> emails = this.avisarContagio();
			if(emails!=null) {
				this.sendMail(emails);
				
			 	PreparedStatement ps = connection.prepareStatement("update Usuario set contacto = 0 where contacto = 1");
				ps.execute();
				connection.commit();
				connection.close();
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

	public ArrayList<String> avisarContagio() throws SQLException {
		ArrayList<String> emails = new ArrayList<>();
		Database database = new Database();
		Connection connection = database.Get_Connection();
		PreparedStatement ps;
		try {
			String sql = "select * from Usuario where contacto = 1";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				emails.add(rs.getString("email"));
			}
			return emails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return null;

	}

	public void sendMail(ArrayList<String> emails) {

		// Put sender�s address
		String from = "agenda.covid.ucudal@gmail.com";
		final String username = "agenda.covid.ucudal@gmail.com";// username generated by Mailtrap
		final String password = "AbalAcostaMarquez.024";// password generated by Mailtrap

		// Paste host address from the SMTP settings tab in your Mailtrap Inbox
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");// it�s optional in Mailtrap
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");// use one of the options in the SMTP settings tab in your Mailtrap Inbox
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		for (String to : emails) {
			try {
				// Create a default MimeMessage object.
				Message message = new MimeMessage(session);

				// Set From: header field
				message.setFrom(new InternetAddress(from));

				// Set To: header field
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

				// Set Subject: header field
				message.setSubject("ALERTA!!! Contacto con positivo por COVID-19!!.");

				// Put the content of your message
				message.setText("Usted fue contacto de un positivo por COVID-19.\n Por favor realice la cuarentena correspondiente y comun�quese inmediatamente con su prestador de salud para realizarse un test PCR. \n Desde ya, muchas gracias.\n\n NOS CUIDAMOS ENTRE TODOS. MSP.");

				// Send message
				Transport.send(message);

				System.out.println("Sent message successfully....");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
