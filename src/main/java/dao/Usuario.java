package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.CupoObject;
import dto.UserObject;

public class Usuario {
	public ArrayList<UserObject> getEstado(Connection connection,String cedula) throws Exception {
		ArrayList<UserObject> user = new ArrayList<UserObject>();
		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM Usuario where cedula = '" + cedula +"'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				UserObject userObject = new UserObject();
				userObject.setCedula(rs.getString("cedula"));
				userObject.setNombre(rs.getString("nombre"));
				userObject.setApellido(rs.getString("apellido"));
				userObject.setFecha_nacimiento(rs.getDate("fec_nac"));
				userObject.setEstado(rs.getInt("estado"));
				user.add(userObject);
			}
			return user;
		} catch (Exception e) {
			throw e;
		}
	}
}
