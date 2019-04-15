package app.exceptions;

public class OwnerDaoException extends Exception {

    String message;

    OwnerDaoException(String str) {
        message = str;
    }

    public String toString() {
        return ("OwnerDaoException Exception Occurred: " + message);
    }
}
