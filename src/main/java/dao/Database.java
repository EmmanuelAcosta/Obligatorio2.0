package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	public Connection Get_Connection() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://agendavacunacion.ch8db7sv9fa2.us-west-1.rds.amazonaws.com:3306/AgendaVacunacion", "admin","AbalAcostaMarquez.024");
			return con;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
