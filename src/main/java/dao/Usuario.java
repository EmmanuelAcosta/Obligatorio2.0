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
				userObject.setTelefono(rs.getString("telefono"));
				user.add(userObject);
			}
			return user;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public boolean insertUser(Connection connection,UserObject userObject) throws Exception {
		String user = "";
		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM Usuario where cedula = '" + userObject.getCedula() +"'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}else {
				String insert = "insert into Usuario (cedula,nombre,apellido,estado,reg_date,fec_nac,email,telefono) values("
						+ userObject.getCedula() + ",'" + userObject.getNombre() + "'"
						+ ",'" + userObject.getApellido()+"'"
								+ ",0,CURDATE()," + "STR_TO_DATE('" + userObject.getFecha_nacimiento()+"','%Y-%m-%d')" + ",'" + userObject.getEmail()+"','" + userObject.getTelefono() + "')";
				PreparedStatement ps2 = connection
						.prepareStatement(insert);
				int rs2 = ps2.executeUpdate();
				connection.commit();
				connection.close();
			}
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
}
