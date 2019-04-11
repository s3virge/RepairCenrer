package app.controllers;

import app.dao.DeviceDao;
import app.models.Device;
import app.models.DeviceAndHisRepair;
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

import java.util.Collection;
import java.util.List;

public class GiveOutDevicePaneController {

    @FXML private ListView lstDeviceList;
    @FXML private Label label;
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private Label label3;
    @FXML private Label label4;
    @FXML private Label label5;
    @FXML private Label label6;
    @FXML private Label label7;
    @FXML private Label label8;
    @FXML private Label label9;
    @FXML private Label diagnosticsResult;
    @FXML private Label repairResult;

    @FXML private Pane labelsPane; //нужна что-бы перебирать все дочерние окна

    private ObservableList<DeviceAndHisRepair> observDeviceList = FXCollections.observableArrayList();

    private static final Logger log = LogManager.getLogger(ReceivedDevicesPaneController.class);

    @FXML
    private void initialize() {
        log.trace("");

        updateDeviceListView(false);
        addDeviceListListener();
        lstDeviceList.getSelectionModel().selectFirst();
    }

    private void updateDeviceListView(boolean okBtn) {
        log.trace("");

        int selectedItem = lstDeviceList.getSelectionModel().getSelectedIndex();

        try {
            Collection<DeviceAndHisRepair> devices = DeviceDao.selectDeviceAndHisRepair(DeviceStatus.READY);
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
                (ChangeListener<DeviceAndHisRepair>) (observable, oldValue, newValue) ->
                {
                    try {
                        label.setText("device id: " + newValue.getDevice().getId());
                        label1.setText("type:  " + newValue.getDevice().getType());
                        label2.setText("brand:  " + newValue.getDevice().getBrand());
                        label3.setText("model:  " + newValue.getDevice().getModel());
                        label4.setText("serial number:  " + newValue.getDevice().getSerialNumber());
                        label5.setText("defect:  " + newValue.getDevice().getDefect());
                        label6.setText("owner id:  " + newValue.getDevice().getOwnerId());
                        label7.setText("repair id:  " + newValue.getDevice().getRepairId());
                        label8.setText("completeness:  " + newValue.getDevice().getCompleteness());
                        label9.setText("appearance:  " + newValue.getDevice().getAppearance());

                        diagnosticsResult.setText(newValue.getRepair().getDiagnosticResult());
                        repairResult.setText(newValue.getRepair().getRepairResult());
                    }
                    catch (NullPointerException npex) {
                        log.error(npex.getMessage());
                    }
                }
        );
    }
}