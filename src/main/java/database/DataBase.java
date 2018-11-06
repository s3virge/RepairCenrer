package database;

import config.Config;
import core.Device;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DataBase {
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return conn;
    }

    private List<Device> findDevice(String pattern) {
        return null;
    }
}
