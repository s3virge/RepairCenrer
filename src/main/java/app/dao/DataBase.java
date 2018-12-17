package app.dao;

import app.config.Config;
import app.utils.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import java.util.stream.Collectors;

public class DataBase {
    public static final Logger logger = LogManager.getLogger(DataBase.class);
//    /**
//     * write to the database Config.DB_NAME the necessary initial data
//     * do not work for mysql.
//     * work fine for postgres
//     */
//    public void initalize() throws URISyntaxException, SQLException, IOException {
//        logger.trace("");
//
//        //        URL uniformResourceLocator = DataBase.class.getClassLoader().getResource("repair_center.sql");
//        URL uniformResourceLocator = getClass().getResource("/sql/createdb.sql");
//
//        Path path = Paths.get(uniformResourceLocator.toURI());
//        List<String> str = Files.readAllLines(path);
//        String sql = str.stream().collect(Collectors.joining());
//
//        try (Connection con = ConnectionBuilder.getConnection();
//             Statement stmt = con.createStatement();
//             ) {
//            stmt.executeUpdate(sql);
//        }
//        catch (Exception e) {
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//
//        logger.info("database '{}' was succesfuly initalized", Config.getProperty(Config.DB_NAME));
//    }

//    /**
//     * print to console resourceFile
//     * @param resourceFile path to file in resource project folder
//     * @throws IOException
//     */
//    public void consolePrintFile(String resourceFile) throws IOException {
//        InputStream in = DataBase.class.getResourceAsStream(resourceFile);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//
//        String line = "";
//
//        while( line !=  null) {
//            line = reader.readLine();
//            System.out.println(line);
//        }
//    }

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
