package app.dao;

import app.dao.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBuilder
{
    public static Connection getConnection() throws SQLException {
        String DbURL = Config.getProperty(Config.DB_URL) +
                Config.getProperty(Config.DB_NAME) + "?useSSL=false&characterEncoding=UTF-8";

        Connection con = DriverManager.getConnection(
                DbURL,
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return con;
    }

    public static Connection getConnectionToServer() throws SQLException {

        Connection con = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL) + "?useSSL=false&characterEncoding=UTF-8",
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return con;
    }

}
