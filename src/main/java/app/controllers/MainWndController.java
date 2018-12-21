package app.controllers;

import app.RepairCenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public class MainWndController {
    private static final Logger logger = LogManager.getLogger(MainWndController.class);

    /**
     * обработка нажатия на пункт меню Новый ремон
     */
    @FXML
    private void menuItemNewRepair() {
        logger.trace("");

        // Загружаем fxml-файл и создаём новую сцену
        // для всплывающего диалогового окна.
        FXMLLoader loader = new FXMLLoader();
        //Sets the location used to resolve relative path attribute values.
        //getResource - Finds a resource with a given name.
        URL resource = getClass().getResource("/dialogs/NewRepairDlg.fxml");
        loader.setLocation(resource);

        AnchorPane repairDlgLayout = null;

        try {
            repairDlgLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }

        // Создаём подмостки для диалогового окна.
        Stage dialogStage = new Stage();
        //подготавливаем их
        dialogStage.setTitle("Новый ремон");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(new RepairCenter().getPrimaryStage());

        //расставляем декорации на сцене согласно плану
        Scene scene = new Scene(repairDlgLayout);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();
    }
}
