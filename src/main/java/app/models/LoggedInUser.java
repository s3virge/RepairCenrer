package app.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * user which logged in
 */
public class LoggedInUser {
    private static Logger log = LogManager.getLogger("LoggedInUser");
    public static User loggedInUser;

    public static void setLoggedInUser(User loggedInUser) {
        LoggedInUser.loggedInUser = loggedInUser;
//        log.debug(loggedInUser);
    }

    public static User getLoggedInUser() {
//        log.debug(loggedInUser);
        return loggedInUser;
    }
}
