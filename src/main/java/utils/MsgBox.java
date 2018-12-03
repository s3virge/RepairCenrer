package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MsgBox {
    public static void show() {
        Exception exception = new Exception("An exception!!!!!!!!!!!!!!!!!");
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("An exception occurred!");
        alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(sw.toString())));
        alert.showAndWait();
    }
}
