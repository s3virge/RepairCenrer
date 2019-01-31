package app.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class ScreenController {
    private static Logger log = LogManager.getLogger("ScreenController");

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

    public ScreenController() {
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

    public void loadFxml(String fxmlFile, Pane pane) {
        log.trace(fxmlFile);
        pane.getChildren().clear();

        FXMLLoader loader = new FXMLLoader();
        //Sets the location used to resolve relative path attribute values.
        //getResource - Finds a resource with a given name.
        URL resource = getClass().getResource(fxmlFile);
        loader.setLocation(resource);

        Pane newLoadedPane = null;

        try {
            newLoadedPane = loader.load();
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }

        pane.getChildren().add(newLoadedPane);
    }

    public FXMLLoader loadDlgFxml(String dlgFxmlFile, String dlgTitle) {
        log.trace(dlgFxmlFile);

        // Загружаем fxml-файл и создаём новую сцену
        // для всплывающего диалогового окна.
        FXMLLoader loader = new FXMLLoader();
        //Sets the location used to resolve relative path attribute values.
        //getResource - Finds a resource with a given name.
        URL resource = getClass().getResource(dlgFxmlFile);
        loader.setLocation(resource);

        Pane repairDlgLayout = null;

        try {
            repairDlgLayout = loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error(e);
        }

        // Создаём подмостки для диалогового окна.
        Stage dialogStage = new Stage();
        //подготавливаем их
        dialogStage.setTitle(dlgTitle);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(ScreenController.getPrimaryStage());

        Scene scene = new Scene(repairDlgLayout);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        dialogStage.showAndWait();
        return loader;
    }
}