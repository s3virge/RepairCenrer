package app.utils;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class ScreenController {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        ScreenController.primaryStage = primaryStage;
    }

    public ScreenController(Stage mainStage) {
        primaryStage = mainStage;
    }

    public ScreenController(Scene main) {
        this.main = main;
    }

    public void add(String name, Pane pane){
        screenMap.put(name, pane);
    }

    public void remove(String name){
        screenMap.remove(name);
    }

    public void activate(String name){
        main.setRoot( screenMap.get(name) );
    }

    public void showDlg(String fxmlFile) {

    }
}