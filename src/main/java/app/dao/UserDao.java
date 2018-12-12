package app.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * класс будет отвечать за работу с данными позьзователя
 */
public class UserDao {
    private static final Logger logger = LogManager.getLogger(UserDao.class);

    private static final String INSERT_USER = "";
    private static final String SELECT_USER = "";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

}
