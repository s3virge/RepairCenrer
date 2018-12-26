package app.dao.config;

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
    public static final String DB_NAME = "db.name";

    private static final String DB_PROPERTIES = "mysql.properties";

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
                        .getResourceAsStream(DB_PROPERTIES);

                properties.load(inStream);
            }
            catch (NullPointerException nullEx) {
                logger.error(nullEx);
            }
            catch (IOException ioEx) {
                logger.error(ioEx);
                throw new RuntimeException("Облом с properties.load(stream);");
            }

            //enable using unicode, but in my case this is do not help
//            properties.put("useUnicode", "true");
//            properties.put("characterEncoding","Cp1251");
//            properties.put("characterEncoding","UTF-8");
//            properties.setProperty("useSSL", "false");
        }

        return properties.getProperty(name);
    }
}