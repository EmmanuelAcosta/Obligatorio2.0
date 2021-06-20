package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dto.ContagioObject;

public class Contagio {
	public boolean insertContagio(Connection connection, ContagioObject contObj) throws Exception {
		try {
			PreparedStatement psUsuario1 = connection.prepareStatement("select * from Usuario where cedula = '" + contObj.getCedula_principal() +"'" );
			PreparedStatement psUsuario2 = connection.prepareStatement("select * from Usuario where cedula = '" + contObj.getCedula_contacto() +"'" );
			ResultSet rs4 = psUsuario2.executeQuery();
			ResultSet rs3 = psUsuario1.executeQuery();
			if(!rs4.next() || !rs4.next()) {
				return false;
			}
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM contacto where (cedula_principal = '"
					+ contObj.getCedula_principal() + "' and cedula_contacto = '" + contObj.getCedula_contacto()
					+ "') or (cedula_principal = '" + contObj.getCedula_contacto() + "' and cedula_contacto = '"
					+ contObj.getCedula_principal() + "')");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				String insert = "insert into contacto (cedula_principal,cedula_contacto,reg_date) values("
						+ contObj.getCedula_principal() + "," + contObj.getCedula_contacto() + "" + ",CURDATE())";
				String insert2 = "insert into contacto (cedula_principal,cedula_contacto,reg_date) values("
						+ contObj.getCedula_contacto() + "," + contObj.getCedula_principal() + "" + ",CURDATE())";
				Statement ps2 = connection.createStatement();
				ps2.addBatch(insert);
				ps2.addBatch(insert2);
				int[] rs2 = ps2.executeBatch();
				connection.commit();
				connection.close();

				return true;
			}
		} catch (Exception e) {
			connection.close();
			connection.rollback();
			e.printStackTrace();
			return false;
		}
	}

	public boolean insertPositivo(Connection connection, ContagioObject contObject) throws SQLException {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Usuario where cedula = '"
					+ contObject.getCedula_principal() + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String update = "Update Usuario set contacto = true where cedula = '" + contObject.getCedula_principal() + "'";
				PreparedStatement ps2 = connection.prepareStatement(update);
				ps2.execute();
				connection.commit();
				connection.close();
				return true;
			} else {
				
				connection.close();

				return false;
			}
		} catch (Exception e) {
			connection.close();
			connection.rollback();
			e.printStackTrace();
			return false;
		}
	}
}
