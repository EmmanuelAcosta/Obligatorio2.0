package model;

import java.sql.Connection;
import java.util.ArrayList;

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

}
