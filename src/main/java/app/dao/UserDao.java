package app.dao;

import app.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * класс будет отвечать за работу с данными позьзователя
 */
public class UserDao {
    private static final Logger logger = LogManager.getLogger(UserDao.class);

    private static final String INSERT_USER = "";


    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

    /**
     * @return
     * возвращает пустого User если в базе нет пользователя с логином strLogin
     */
    public User getUserByLogin(String strLogin) {
        logger.trace("");

        final String SELECT_USER = "SELECT user.id, user.login, user.password, user_group.value " +
                "FROM user INNER JOIN user_group ON user.user_group = user_group.id " +
                "where user.login='" + strLogin + "';";

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


    /**
     * @return list of masters
     */
    public User getListOfMasters() {
        logger.trace("");

        final String SELECT_МASTER = "SELECT * FROM user where user_group.id = 3";

        User user = new User();

        try (Connection co = ConnectionBuilder.getConnection();
             Statement st = co.createStatement()) {
            ResultSet result = st.executeQuery(SELECT_МASTER);
            while (result.next()) {
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("login"));
//                user.setPassword(result.getString("password"));
                user.setSurname(result.getString("surname"));
                user.setName(result.getString("name"));
                user.setPatronymic(result.getString("patronymic"));
            }
        }
        catch (SQLException sex) {
            logger.error(sex.getMessage());
        }

        return user;
    }

}
