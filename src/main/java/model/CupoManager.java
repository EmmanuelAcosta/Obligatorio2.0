package model;

import java.sql.Connection;
import java.util.ArrayList;

import dao.Cupo;
import dao.Database;
import dto.CupoObject;

public class CupoManager {

	public ArrayList<CupoObject> getCupos() throws Exception {
		ArrayList<CupoObject> cupos = null;
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Cupo cupo = new Cupo();
			cupos = cupo.GetCupos(connection);
		} catch (Exception e) {
			throw e;
		}
		return cupos;
	}

	public boolean setCupos(String cedula,String codigo_reserva) throws Exception {
		boolean databaseState = false;
		try {
			Database database = new Database();
			Connection connection = database.Get_Connection();
			Cupo cupo = new Cupo();
			databaseState = cupo.SetCupos(connection,cedula,codigo_reserva);
		} catch (Exception e) {
			throw e;
		}
		return databaseState;
	}

}