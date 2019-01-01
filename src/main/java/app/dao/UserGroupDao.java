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

	/**
	 * @return list of groups names
	 */
	public static StringVector getList() {
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
}