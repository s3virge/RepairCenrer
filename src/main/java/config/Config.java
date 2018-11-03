package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";

    private static Properties properties;

    public static String getProperty(String name) {
        if (properties == null) {

            //ResourceBundle properties = ResourceBundle.getBundle("dataBase");

            /*
            * Java Classloader(завантажувач класів) є частиною
            * Java Runtime Environment, що динамічно завантажує Java-класи в Java Virtual Machine.
            *
            * getResourceAsStream - Returns an input stream for reading the specified resource.*/
//            try (InputStream inStream = Config.class.getClassLoader()
//                    .getResourceAsStream("db.properties")) {
//
//                properties.load(inStream);
//            }
//            catch (IOException ie) {
//                ie.printStackTrace();
//                System.out.println("Облом с чтением db.properties");
//                throw new RuntimeException();
//            }

            try {
                InputStream inStream = Config.class.getClassLoader()
                        .getResourceAsStream("db.properties");

                properties.load(inStream);
            }
            catch (NullPointerException nullEx) {
                throw new NullPointerException("Облом с properties.load(inStream);");
            }
            catch (IOException ioEx) {
                throw new RuntimeException("Облом с properties.load(inStream);");
            }
        }
        return properties.getProperty(name);
    }
}