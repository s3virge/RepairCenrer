package app.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Logger logger = LogManager.getLogger(Config.class);

    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";

    private static Properties properties = new Properties();

    public static String getProperty(String name) {
        if (properties.isEmpty()) {
            /*
            * Java Classloader(завантажувач класів) є частиною
            * Java Runtime Environment, що динамічно завантажує Java-класи в Java Virtual Machine.
            *
            * getResourceAsStream - Returns an input stream for reading the specified resource.*/
            try {
                InputStream inStream = Config.class.getClassLoader()
                        .getResourceAsStream("db.properties");

                properties.load(inStream);
            }
            catch (NullPointerException nullEx) {
                logger.error(nullEx);
            }
            catch (IOException ioEx) {
                logger.error(ioEx);
                throw new RuntimeException("Облом с properties.load(stream);");
            }
        }

        return properties.getProperty(name);
    }
}