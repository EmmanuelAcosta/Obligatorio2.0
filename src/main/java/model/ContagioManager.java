package model;

import java.sql.Connection;

import dao.Contagio;
import dao.Database;
import dto.ContagioObject;

public class ContagioManager {

	
	public boolean insertUser(ContagioObject contObject) throws Exception {
		boolean cont =false;
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Contagio contagio= new Contagio();
			cont = contagio.insertContagio(connection, contObject);
		} catch (Exception e) {
			throw e;
		}
		return cont;
	}

}
