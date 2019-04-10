package app.controllers;

import app.dao.DeviceDao;
import app.models.Device;
import app.models.DeviceStatus;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GiveOutDevicePaneController {

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
    private Pane labelsPane;

    private ObservableList<Device> observDeviceList = FXCollections.observableArrayList();

    private static final Logger log = LogManager.getLogger(ReceivedDevicesPaneController.class);

    @FXML
    private void initialize() {
        log.trace("");
        //todo obtain from database devices with status ready

        updateDeviceListView(false);
        addDeviceListListener();
        lstDeviceList.getSelectionModel().selectFirst();
    }

    private void updateDeviceListView(boolean okBtn) {
        log.trace("");

        int selectedItem = lstDeviceList.getSelectionModel().getSelectedIndex();

        try {
            List devices = DeviceDao.select(DeviceStatus.READY);
            observDeviceList.clear();
            clearFields(labelsPane);

            if (devices.isEmpty()) {
                log.trace("devices list is empty");
                return;
            }

            observDeviceList.addAll(devices);
            lstDeviceList.setItems(observDeviceList);

            if (okBtn) {
                lstDeviceList.getSelectionModel().selectLast();
            }
            else {
                lstDeviceList.getSelectionModel().select(selectedItem);
            }
        }
        catch (NullPointerException npex) {
            log.error(npex.getMessage());
        }
    }

    private void clearFields(Pane pane) {
        try {
            for (Node node : pane.getChildren()) {
                if (node instanceof TextField) {
                    // clear
                    ((TextField)node).setText("");
                }
                else if (node instanceof Label) {
                    // clear
                    ((Label)node).setText("");
                }
            }
        }
        catch (NullPointerException npex) {
            log.error(npex.getMessage());
        }
    }

    private void addDeviceListListener() {
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