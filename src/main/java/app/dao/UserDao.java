package app.dao;

import app.models.User;
import app.models.UserGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Vector;

/**
 * класс будет отвечать за работу с данными позьзователя
 */
public class UserDao {
    private static final Logger logger = LogManager.getLogger(UserDao.class);
    private static final String tableName = "user";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

    /**
     * @return
     * возвращает пустого User если в базе нет пользователя с логином strLogin
     */
    public User selectByLogin(String strLogin) {
        logger.trace("");

        final String SELECT_USER = "SELECT user.id, user.login, user.password, user_group.value " +
                "FROM user INNER JOIN user_group ON user.user_group = user_group.id " +
                "where user.login='" + strLogin + "'";

        User user = new User();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            /*
            выбрать id, login. password, value
            из user внутренне соединенной с user_group через user.user_group с user_group.id
            где login = admin
            * */
            ResultSet rs = stmt.executeQuery(SELECT_USER);

            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                //получить значение столбца value из связанной таблицы
                user.setGroup(rs.getString("value"));
            }
        }
        catch (SQLException exception) {
            logger.error(exception);
            System.exit(0);
        }

        return user;
    }

    public static int selectIdByFullName(String fullName) {
        logger.trace("");

        String splitFullName[] = fullName.split(" ");

        int id = 0;

        final String select_fullname_id = String.format("select id from user " +
                        "where surname = '%s' and name = '%s' and patronymic = '%s'",
                splitFullName[0], splitFullName[1], splitFullName[2]);

        try (Connection dbConn = ConnectionBuilder.getConnection();
             Statement statement = dbConn.createStatement()) {
            ResultSet rs = statement.executeQuery(select_fullname_id);
            while (rs.next()) {
                id = rs.getInt("id");
            }
        }
        catch (SQLException sEx) {
            logger.error(sEx.getMessage());
        }

        return id;
    }

    /**
     * @return list of masters
     */
    public static Vector<User> selectMasters() {
        logger.trace("");

        final String SELECT_МASTER = "SELECT user.id, user.login, user.password, " +
                "user_group.value, " +
                "user.surname, user.name, user.patronymic " +
                "FROM user " +
                "INNER JOIN user_group ON user.user_group = user_group.id " +
                "WHERE user_group.value = '" + UserGroup.MASTER + "'";

        Vector<User> listOfMasters = new Vector<>();

        try (Connection co = ConnectionBuilder.getConnection();
             Statement st = co.createStatement()) {
            ResultSet result = st.executeQuery(SELECT_МASTER);
            while (result.next()) {
                User user = new User();

//                user.setId(result.getInt("id"));
//                user.setLogin(result.getString("login"));
//                user.setPassword(result.getString("password"));
////                user.setGroup(result.getString("user_group"));
//                user.setGroup(result.getString("value"));
                user.setSurname(result.getString("surname"));
                user.setName(result.getString("name"));
                user.setPatronymic(result.getString("patronymic"));

                listOfMasters.add(user);
            }
        }
        catch (SQLException sex) {
            logger.error(sex.getMessage());
        }

        return listOfMasters;
    }

    public static Vector<User> selectAll() {
        Vector<User> listOfUsers = new Vector<>();

        final String select_all = "SELECT user.id, user.login, user.password, user_group.value, " +
                "user.surname, user.name, user.patronymic, user.phone_number, user.email " +
                "FROM user " +
                "INNER JOIN user_group ON user.user_group = user_group.id;";

        try (Connection con = ConnectionBuilder.getConnection();
            Statement st = con.createStatement()) {

            ResultSet resultSet = st.executeQuery(select_all);

            while (resultSet.next()) {
                User currentUser = new User();
                currentUser.setId(resultSet.getInt("id"));
                currentUser.setLogin(resultSet.getString("login"));
                currentUser.setPassword(resultSet.getString("password"));
                currentUser.setGroup(resultSet.getString("value"));
                currentUser.setSurname(resultSet.getString("surname"));
                currentUser.setName(resultSet.getString("name"));
                currentUser.setPatronymic(resultSet.getString("patronymic"));
                currentUser.setPhoneNumber(resultSet.getString("phone_number"));
                currentUser.setEmail(resultSet.getString("email"));

                listOfUsers.add(currentUser);
            }
        }
        catch (SQLException sq) {
            logger.error(sq.getMessage());
        }
        return listOfUsers;
    }

    /**
     * insert user to column user in database
     * @param userToSave
	 * @return last inserted id
     */
    public static int insert(User userToSave) {
		int id = 0;

        final String insert_user = "insert into " + tableName + "(login, password, user_group, " +
                "surname, name, patronymic, phone_number, email) " +
                "values(?,?,?,?,?,?,?,?)";

        int userGroupId = UserGroupDao.selectId(userToSave.getGroup());

        try (Connection conn = ConnectionBuilder.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert_user)) {

        	stmt.setString(1, userToSave.getLogin());
            stmt.setString(2, userToSave.getPassword());
            stmt.setInt(3, userGroupId);
            stmt.setString(4, userToSave.getSurname());
            stmt.setString(5, userToSave.getName());
            stmt.setString(6, userToSave.getPatronymic());
            stmt.setString(7, userToSave.getPhoneNumber());
            stmt.setString(8, userToSave.getEmail());

			stmt.execute();

			ResultSet rs;

			rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

			if (rs.next()) {
				id = rs.getInt(1);
			}
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
        }

        return id;
    }

    public static void delete(int id) {
        final String delete_user = "DELETE FROM " + tableName + " WHERE id = " + id;

        try (Connection dbConn = ConnectionBuilder.getConnection();
             Statement statement = dbConn.createStatement()) {
            statement.execute(delete_user);
        }
        catch (SQLException exc) {
            logger.error(exc.getMessage());
        }
    }

    public static void update(User userToUpdate) {
        logger.trace("");
        /*
		UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;
		*/

        final String update_user = String.format("update %s " +
                "set surname = '%s', name = '%s', patronymic = '%s', " +
                "login = '%s', password = '%s', user_group = %d, " +
                "phone_number = '%s', email = '%s' " +
                "where id = %d", tableName,
                userToUpdate.getSurname(), userToUpdate.getName(), userToUpdate.getPatronymic(),
                userToUpdate.getLogin(), userToUpdate.getPassword(), UserGroupDao.selectId(userToUpdate.getGroup()),
                userToUpdate.getPhoneNumber(), userToUpdate.getEmail(), userToUpdate.getId());

        try (Connection con = ConnectionBuilder.getConnection();
             Statement st = con.createStatement()) {
            st.execute(update_user);
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }
}
