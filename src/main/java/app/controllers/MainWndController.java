package app.controllers;

import app.dao.DeviceDao;
import app.models.Device;
import app.models.DeviceStatus;
import app.models.LoggedInUser;
import app.utils.ScreenController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainWndController {
    private static final Logger log = LogManager.getLogger(MainWndController.class);

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

    @FXML
    private Menu mLoggedInUser;

    private ObservableList<Device> observDeviceList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        log.trace("");

        initListView(false);
        lstDeviceList.getSelectionModel().selectFirst();
        mLoggedInUser.setText(LoggedInUser.getLoggedInUser().getLogin());
    }

    private void initListView(boolean okBtn) {
        int selectedItem = lstDeviceList.getSelectionModel().getSelectedIndex();

        try {
            //покажем в списке устройства со статусом Принято
            List devices = DeviceDao.selectByStatus(DeviceStatus.received);

            clearFields();
            observDeviceList.addAll(devices);
            lstDeviceList.setItems(observDeviceList);

            if (okBtn) {
                lstDeviceList.getSelectionModel().selectLast();
            }
            else {
                lstDeviceList.getSelectionModel().select(selectedItem);
            }

            deviceListListener();
        }
        catch (NullPointerException npex) {
            log.error(npex.getMessage());
        }
    }

    /**
     * обработка нажатия на пункт меню Новый ремон
     */
    @FXML
    private void showReceiveNewDeviceDlg() {
        log.trace("");

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
            log.error(e);
        }

        // Создаём подмостки для диалогового окна.
        Stage dialogStage = new Stage();
        //подготавливаем их
        dialogStage.setTitle("Оформить устройство");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(ScreenController.getPrimaryStage());

        //расставляем декорации на сцене согласно плану
        Scene scene = new Scene(repairDlgLayout);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();

        NewRepairDialogController controller = loader.getController();
        initListView(controller.okBtnPressed());
    }

    @FXML
    private void showUserManagementDlg() {
        log.trace("");

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
            log.error(e.getMessage());
        }

        // Создаём подмостки для диалогового окна.
        Stage dialogStage = new Stage();
        //подготавливаем их
        dialogStage.setTitle("Пользователи");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(ScreenController.getPrimaryStage());

        //расставляем декорации на сцене согласно плану
        Scene scene = new Scene(userDlgLayout);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();
    }

    @FXML
    private void showGiveOutDlg() {

    }

    @FXML
    private void selectDevicesInRepair() {

    }

    @FXML
    private void logoff() {
        log.trace("");

        Parent layout = null;
        String sceneFile = "/view/loginWindow/LoginWnd.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();

        try {
            fxmlLoader.setLocation(getClass().getResource(sceneFile));
            layout = fxmlLoader.load();
        }
        catch (Exception ex) {
            log.error(ex);
        }

        //показываем окно ввода логина и пароля
        Scene scene = new Scene(layout, 360, 220);

        Stage primaryStage = ScreenController.getPrimaryStage();
        primaryStage.setTitle("A simple database of the service center");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void selectDevicesOnDiagnostics() {
        //покажем в списке устройства со статусом Принято
        List devices = DeviceDao.selectByStatus(DeviceStatus.diagnostics);

        clearFields();

        observDeviceList.addAll(devices);
        lstDeviceList.setItems(observDeviceList);
        lstDeviceList.getSelectionModel().selectFirst();

        deviceListListener();
    }

    private void clearFields() {
        try {
            observDeviceList.clear();
            label.setText("");
            label1.setText("");
            label2.setText("");
            label3.setText("");
            label4.setText("");
            label5.setText("");
            label6.setText("");
            label7.setText("");
            label8.setText("");
            label9.setText("");
        }
        catch (NullPointerException npex) {
            log.error(npex.getMessage());
        }
    }

    @FXML
    private void selectReceivedDevices() {
        //покажем в списке устройства со статусом Принято
        List devices = DeviceDao.selectByStatus(DeviceStatus.received);

        clearFields();

        observDeviceList.addAll(devices);
        lstDeviceList.setItems(observDeviceList);
        lstDeviceList.getSelectionModel().selectFirst();

        deviceListListener();
    }

    private void deviceListListener() {
        lstDeviceList.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<Device>) (observable, oldValue, newValue) ->
                {
                    try {
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
                    catch (NullPointerException npex) {
                        log.error(npex.getMessage());
                    }
                }
        );
    }
}
