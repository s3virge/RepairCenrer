package app.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    /**
     * создать базу данных
     */
    public void create() throws URISyntaxException, SQLException, IOException {
        //open sql script
        //execute sql script

//        URL uniformResourceLocator = DataBase.class.getClassLoader().getResource("repair_center.sql");
        URL uniformResourceLocator = getClass().getResource("/sql/repair_center.sql");

        Path path = Paths.get(uniformResourceLocator.toURI());
        List<String> str = Files.readAllLines(path);
        String sql = str.stream().collect(Collectors.joining());

        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
             ) {
            stmt.executeUpdate(sql);
        }
    }

    /**
     * print to console resourceFile
     * @param resourceFile path to file in resource project folder
     * @throws IOException
     */
    public void consolePrintFile(String resourceFile) throws IOException {
        InputStream in = DataBase.class.getResourceAsStream(resourceFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line = "";

        while( line !=  null) {
            line = reader.readLine();
            System.out.println(line);
        }
    }

    /**
     * проверить существование базы данных
     * @return true if data base is exist
     */
    public boolean isExists() {
        logger.trace("");

//        Connection connection = null;
//        Statement statement = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost/",
//                    "root", "admin");
//            statement = connection.createStatement();
//            String sql = "CREATE DATABASE DBNAME";
//            //To delete database: sql = "DROP DATABASE DBNAME";
//            statement.executeUpdate(sql);
//            System.out.println("Database created!");
//        } catch (SQLException sqlException) {
//            if (sqlException.getErrorCode() == 1007) {
//                // Database already exists error
//                System.out.println(sqlException.getMessage());
//            } else {
//                // Some other problems, e.g. Server down, no permission, etc
//                sqlException.printStackTrace();
//            }
//        } catch (ClassNotFoundException e) {
//            // No driver class found!
//        }
        // close statement & connection

        return false;
    }
}
