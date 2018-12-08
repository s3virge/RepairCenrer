package app.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MsgBox {

//    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_BLACK = "\u001B[30m";
//    public static final String ANSI_RED = "\u001B[31m";
//    public static final String ANSI_GREEN = "\u001B[32m";
//    public static final String ANSI_YELLOW = "\u001B[33m";
//    public static final String ANSI_BLUE = "\u001B[34m";
//    public static final String ANSI_PURPLE = "\u001B[35m";
//    public static final String ANSI_CYAN = "\u001B[36m";
//    public static final String ANSI_WHITE = "\u001B[37m";

    public enum Type {
        MB_ERROR,
        MB_INFO,
        MB_WARNING
    }

    /**
     *
     * @param msgText Message text
     * @param msgType Message type. Can be one of values MB_ERROR, MB_INFO, MB_WARNING
     */
    public static void show(String msgText,  Type msgType) {

       AlertType alertType = AlertType.NONE;
       String titleBar = "None";

       if (msgType != null) {
           switch (msgType) {
               case MB_ERROR:
                   alertType = AlertType.ERROR;
                   titleBar = "Ошибка";
                   break;
               case MB_INFO:
                   alertType = AlertType.INFORMATION;
                   titleBar = "Информация";
                   break;
               case MB_WARNING:
                   alertType = AlertType.WARNING;
                   titleBar = "Внимание";
                   break;
           }
       }

        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        String errMessage = "Файл: " + ste.getFileName() + "\n" +
                //ste.getClassName() + ": " +
                "Метод: " + ste.getMethodName() + "\n" +
                "Строка: " + ste.getLineNumber();

        Alert alert = new Alert(alertType);
        alert.setTitle(titleBar);
        alert.setHeaderText(msgText);
//      alert.setContentText(msgText);
        alert.setContentText(errMessage);
        alert.showAndWait();
    }

    /**
     * @param msg
     * @param exception
     */
    public static void show(String msg, Exception exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));

        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        String errMessage = "Файл: " + ste.getFileName() + "\n" +
                //ste.getClassName() + ": " +
                "Метод: " + ste.getMethodName() + "\n" +
                "Строка: " + ste.getLineNumber();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(msg);
        alert.setContentText(errMessage);
        alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(sw.toString())));
        alert.showAndWait();
    }

    public static void show(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
      alert.setContentText(message);
//        alert.setContentText(errMessage);
        alert.showAndWait();
    }

//    //info
//    public static void info(String message) {
//       System.out.println(ANSI_GREEN + message + ANSI_RESET);
//    }
//
//    //error
//    public static void error(String message) {
//       System.out.println(ANSI_RED + message + ANSI_RESET);
//    }
//
//    //debug
//    public static void debug(String message) {
//       System.out.println(ANSI_BLUE + message + ANSI_RESET);
//    }
//
//    //warning
//    public static void warning(String message) {
//       System.out.println(ANSI_YELLOW + message + ANSI_RESET);
//    }
}