package model;

import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.QueryParam;

import dao.Cupo;
import dao.Database;
import dao.Usuario;
import dto.CupoObject;
import dto.UserObject;

public class UserManager {
	public ArrayList<UserObject> getEstado(String cedula) throws Exception {
		ArrayList<UserObject> user = new ArrayList<>();
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Usuario usuario = new Usuario();
			user = usuario.getEstado(connection,cedula);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}
	
	public boolean insertUser(UserObject userObject) throws Exception {
		boolean user =false;
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Usuario usuario = new Usuario();
			user = usuario.insertUser(connection, userObject);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

}
