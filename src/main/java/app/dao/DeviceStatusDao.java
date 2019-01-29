package app.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeviceStatusDao {
	private static final Logger log = LogManager.getLogger("DeviceStatusDao");
	private static final String tableName = "status";

	public static int selectId(String devStatus) {
		int statusId = 0;

		try (Connection conn = ConnectionBuilder.getConnection();
			 Statement stmt = conn.createStatement()) {
			final String select_id = "select id from " + tableName + " where value = '" + devStatus + "'";
			ResultSet resultSet = stmt.executeQuery(select_id);
			while (resultSet.next()) {
				statusId = resultSet.getInt("id");
			}
		}
		catch (SQLException sqlExc) {
			log.error(sqlExc.getMessage());
		}

		return statusId;
	}
}
