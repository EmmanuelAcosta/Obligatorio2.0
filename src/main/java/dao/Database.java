package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	public Connection Get_Connection() {
		String retorno = "";
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://agendavacunacion.ch8db7sv9fa2.us-west-1.rds.amazonaws.com:3306/AgendaVacunacion","admin","AbalAcostaMarquez.024");
			con.setAutoCommit(false);
			return con;
			}catch(Exception e){ System.out.println(e);}  
			  
			
		return null;
	}
}
