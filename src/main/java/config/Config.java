package config;

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
            InputStream inStream = Config.class.getClassLoader()
                    .getResourceAsStream("bd.properties");

            Config.class
        }
        return properties.getProperty(name);
    }
}