package app.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MsgBox {

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

    /**
     * тип по умолчанию ERROR
     * @param message текст сообщения
     */
    public static void show(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}