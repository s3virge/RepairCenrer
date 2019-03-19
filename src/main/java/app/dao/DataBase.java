package app.dao;

import app.dao.config.Config;
import app.utils.MsgBox;
import app.utils.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    public static final Logger logger = LogManager.getLogger(DataBase.class);

    /**
     * check if database exists
     * @return true if data base is exist
     */
    public boolean isExists() {
        logger.trace("");

        boolean result = false;

        //тут пытаемся подключиться к базе данных Config.DB_NAME
        try (Connection conn = ConnectionBuilder.getConnection();
             Statement stmt = conn.createStatement())
        {
            logger.info("successfully connected to database {}", Config.getProperty(Config.DB_NAME));
            result = true;
        }
        catch (SQLException sqlException) {
            logger.error(sqlException.getMessage());
            result = false;
        }

        return result;
    }

    /**
     * create database Config.DB_NAME
     */
    public void create() {
        logger.trace("");

        try (Connection con = ConnectionBuilder.getConnectionToServer()) {
            ScriptRunner runner = new ScriptRunner(con, false, true);

            URL uniformResourceLocator = getClass().getResource("/sql/createdb.sql");
            Path path = Paths.get(uniformResourceLocator.toURI());

            BufferedReader reader = Files.newBufferedReader(path);
            runner.runScript(reader);
        }
        catch (SQLException | IOException | URISyntaxException ex) {
            logger.error(ex.getMessage());
//            throw new RuntimeException();
//            MsgBox.show(ex.getMessage(), MsgBox.Type.MB_ERROR);
            //FX not loaded yet
            //todo what to do if mysql does not installed
            JOptionPane.showMessageDialog(null, "Облом с созданием базы данных.", "Сильный облом", JOptionPane.ERROR_MESSAGE);
            //todo stop main process when error
            System.exit(0);
        }

        logger.info("database '{}' was created successfuly", Config.getProperty(Config.DB_NAME));
    }

    public void drop() {
        logger.trace("");
        try (Connection con = ConnectionBuilder.getConnectionToServer();
             Statement statement = con.createStatement())
        {
            String sql = "drop database `" + Config.getProperty(Config.DB_NAME) + "`;" ;
            statement.execute(sql);
        }
        catch (SQLException sqlEx) {
            logger.error(sqlEx.getMessage());
        }

       logger.info("Database '{}' was successfuly droped", Config.getProperty(Config.DB_NAME));
    }
}
