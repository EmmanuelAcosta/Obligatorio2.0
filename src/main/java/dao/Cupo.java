package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.CupoObject;
import dto.FeedObjects;

public class Cupo {
	public ArrayList<CupoObject> GetCupos(Connection connection) throws Exception {
		ArrayList<CupoObject> cupoData = new ArrayList<CupoObject>();
		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM Cupo");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CupoObject cupoObject = new CupoObject();
				cupoObject.setLugar(rs.getString("lugar"));
				cupoObject.setCodigo_reserva(rs.getString("codigo_reserva"));
				cupoObject.setEstado(rs.getInt("estado"));
				cupoObject.setFec_primer_dosis(rs.getDate("fec_primer_dosis"));
				cupoObject.setFec_segunda_dosis(rs.getDate("fec_segunda_dosis"));
				cupoObject.setLocalidad(rs.getString("localidad"));
				cupoData.add(cupoObject);
			}
			return cupoData;
		} catch (Exception e) {
			throw e;
		}
	}
}
