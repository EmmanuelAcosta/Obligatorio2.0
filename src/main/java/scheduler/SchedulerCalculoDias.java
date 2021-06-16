package scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;

import dao.Database;

public class SchedulerCalculoDias extends TimerTask {
	private String name;

	public SchedulerCalculoDias(String n) {
		this.name = n;
	}

	public String returnName() {
		return this.name;
	}
	@Override
	public void run() {
		try {
			this.deleteOld();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteOld() throws SQLException {
		Database database = new Database();
		Connection connection = database.Get_Connection();
		PreparedStatement ps;
		try {
			String sql = "select cedula_principal,cedula_contacto from contacto where (SELECT DATEDIFF(NOW(),reg_date)) > 0";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PreparedStatement ps2 = connection.prepareStatement(
						"delete from contacto where cedula_principal = '" + rs.getString("cedula_principal")
								+ "' and cedula_contacto = '" + rs.getString("cedula_contacto") + "'");
				ps2.execute();
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			connection.rollback();
			connection.close();
			e.printStackTrace();
		}

	}

}
