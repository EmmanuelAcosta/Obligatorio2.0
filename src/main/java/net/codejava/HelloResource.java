package net.codejava;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/bonjour")
public class HelloResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String direBonjour() {
		String retorno = "";
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://agendavacunacion.ch8db7sv9fa2.us-west-1.rds.amazonaws.com:3306/AgendaVacunacion","admin","AbalAcostaMarquez.024");  
			//here sonoo is database name, root is username and password  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from MyGuests");  
			while(rs.next())  
			System.out.println(rs.getString("firstname"));  
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
			  
			
		return "List";
	}
}