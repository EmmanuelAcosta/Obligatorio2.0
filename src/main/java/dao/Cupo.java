package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.logging.Logger;	
import java.util.logging.Level;

import dto.CupoObject;
import dto.FeedObjects;

public class Cupo {
	public ArrayList<CupoObject> GetCupos(Connection connection) throws Exception {
		ArrayList<CupoObject> cupoData = new ArrayList<CupoObject>();
		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM Cupo where estado = 0");
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
			connection.close();
			return cupoData;
		} catch (Exception e) {
			connection.close();
			throw e;
		}
	}

	public boolean SetCupos(Connection connection,String cedula,String codigo_reserva) throws Exception {
		try {
			Statement s = connection.createStatement();
			s.addBatch("insert into Cupo_Usuario (cedula,codigo_reserva) values('"+cedula +"','"+ codigo_reserva +"')");
			s.addBatch("update Usuario set estado = 1 where cedula = '" + cedula +"'");
			s.addBatch("update Cupo set estado = 1 where codigo_reserva = '" + codigo_reserva + "'");
			s.executeBatch();
			connection.commit();
			connection.close();
			
			return true;
		} catch (Exception e) {
			try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(Cupo.class.getName()).log(Level.SEVERE, null, ex);
            }
			throw e;
		}
	}
}
