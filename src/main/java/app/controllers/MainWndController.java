package app.controllers;

import app.RepairCenter;
import app.dao.DeviceDao;
import app.models.Device;
import app.models.DeviceStatus;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainWndController {
    private static final Logger logger = LogManager.getLogger(MainWndController.class);

    @FXML
    private ListView lstDeviceList;
    @FXML
    private Label label;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
 @FXML
    private Label label4;
 @FXML
    private Label label5;
 @FXML
    private Label label6;
 @FXML
    private Label label7;
 @FXML
    private Label label8;
 @FXML
    private Label label9;

    ObservableList<Device> observDeviceList = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        initListView();
        lstDeviceList.getSelectionModel().selectFirst();

        lstDeviceList.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<Device>) (observable, oldValue, newValue) ->
                {
                    label.setText("device id: " + newValue.getId());
                    label1.setText("type:  " + newValue.getType());
                    label2.setText("brand:  " + newValue.getBrand());
                    label3.setText("model:  " + newValue.getModel());
                    label4.setText("serial number:  " + newValue.getSerialNumber());
                    label5.setText("defect:  " + newValue.getDefect());
                    label6.setText("owner id:  " + newValue.getOwnerId());
                    label7.setText("repair id:  " + newValue.getRepairId());
                    label8.setText("completeness:  " + newValue.getCompleteness());
                    label9.setText("appearance:  " + newValue.getAppearance());
                }
        );
    }

    private void initListView() {
        int selectedItem = lstDeviceList.getSelectionModel().getSelectedIndex();
        observDeviceList.clear();
        //покажем в списке устройства со статусом Принято
        List devices = DeviceDao.selectByStatus(DeviceStatus.received);

        observDeviceList.addAll(devices);
        lstDeviceList.setItems(observDeviceList);
        lstDeviceList.getSelectionModel().select(selectedItem);
    }

    /**
     * обработка нажатия на пункт меню Новый ремон
     */
    @FXML
    private void showNewRepairDlg() {
        logger.trace("");

        // Загружаем fxml-файл и создаём новую сцену
        // для всплывающего диалогового окна.
        FXMLLoader loader = new FXMLLoader();
        //Sets the location used to resolve relative path attribute values.
        //getResource - Finds a resource with a given name.
        URL resource = getClass().getResource("/view/dialogs/NewRepairDlg.fxml");
        loader.setLocation(resource);

        AnchorPane repairDlgLayout = null;

        try {
            repairDlgLayout = loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }

        // Создаём подмостки для диалогового окна.
        Stage dialogStage = new Stage();
        //подготавливаем их
        dialogStage.setTitle("Оформить устройство");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(new RepairCenter().getPrimaryStage());

        //расставляем декорации на сцене согласно плану
        Scene scene = new Scene(repairDlgLayout);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();

        initListView();
    }

    @FXML
    private void showUserManagementDlg() {
        logger.trace("");

        // Загружаем fxml-файл и создаём новую сцену
        // для всплывающего диалогового окна.
        FXMLLoader loader = new FXMLLoader();
        //Sets the location used to resolve relative path attribute values.
        //getResource - Finds a resource with a given name.
        URL resource = getClass().getResource("/view/dialogs/UserManagementDlg.fxml");
        loader.setLocation(resource);

        Parent userDlgLayout = null;

        try {
            userDlgLayout = loader.load();
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }

        // Создаём подмостки для диалогового окна.
        Stage dialogStage = new Stage();
        //подготавливаем их
        dialogStage.setTitle("Пользователи");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(new RepairCenter().getPrimaryStage());

        //расставляем декорации на сцене согласно плану
        Scene scene = new Scene(userDlgLayout);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();
    }
}
