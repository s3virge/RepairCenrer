package app.dao;

import com.sun.org.apache.xml.internal.utils.StringVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserGroupDao {
	private static final Logger log = LogManager.getLogger();
	private static final String tableName = "user_group";

	/**
	 * @return list of groups names
	 */
	public static StringVector selectAll() {
		log.trace("");

		final String select_user_group = "select * from user_group";
		StringVector list = new StringVector();

		try (Connection con = ConnectionBuilder.getConnection();
			 Statement statement = con.createStatement()) {

			ResultSet result = statement.executeQuery(select_user_group);

			while (result.next()) {
				String groupName = result.getString("value");
				list.push(groupName);
			}
		}
		catch (SQLException sqlException) {
			log.error(sqlException.getMessage());
		}

		return list;
	}

	public static int selectId(String group) {
		log.trace("");

		int id = 0;

		final String select_id = "select id from " + tableName + " where value = '" + group + "'";

		try (Connection con = ConnectionBuilder.getConnection();
			 Statement stm = con.createStatement()) {

			ResultSet res = stm.executeQuery(select_id);

			while (res.next()) {
				id = res.getInt("id");
			}
		}
		catch (SQLException ex) {
			log.error(ex.getMessage());
		}

		return id;
	}
}