package app.controllers;

import app.dao.DeviceDao;
import app.models.Device;
import app.models.DeviceStatus;
import app.models.LoggedInUser;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DevicesOnDiagnosticsPaneController {

    @FXML
    private ListView lstDeviceList;
    @FXML
    private Label labelId;
    @FXML
    private Label labelType;
    @FXML
    private Label labelBrand;
    @FXML
    private Label labelModel;
    @FXML
    private Label labelSerialNumber;
    @FXML
    private Label labelDefect;
    @FXML
    private Label labelOwnerId;
    @FXML
    private Label labelRepairId;
    @FXML
    private Label labelCompleteness;
    @FXML
    private Label labelAppearance;

    private ObservableList<Device> observDeviceList = FXCollections.observableArrayList();

//    private final String today = gerCurrentDate();

    private static final Logger log = LogManager.getLogger(DevicesOnDiagnosticsPaneController.class);

    @FXML
    private void initialize() {
        log.trace("");


        updateDeviceListView(false);
        addDeviceListListener();
        lstDeviceList.getSelectionModel().selectFirst();
    }

    private void updateDeviceListView(boolean okBtn) {
        int selectedItem = lstDeviceList.getSelectionModel().getSelectedIndex();

        try {
            List devices = DeviceDao.selectByStatusAndMaster(DeviceStatus.diagnostics, LoggedInUser.getLoggedInUser().getLogin());

            clearFields();
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

    private void clearFields() {
        try {
            observDeviceList.clear();
            labelId.setText("");
            labelType.setText("");
            labelBrand.setText("");
            labelModel.setText("");
            labelSerialNumber.setText("");
            labelDefect.setText("");
            labelOwnerId.setText("");
            labelRepairId.setText("");
            labelCompleteness.setText("");
            labelAppearance.setText("");
        }
        catch (NullPointerException npex) {
            log.error(npex.getMessage());
        }
    }

    private String gerCurrentDate() {
        //Current Date
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDate.format(formatter);
    }

    private void addDeviceListListener() {
        lstDeviceList.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<Device>) (observable, oldValue, newValue) ->
                {
                    try {
                        labelId.setText("device id: " + newValue.getId());
                        labelType.setText("type:  " + newValue.getType());
                        labelBrand.setText("brand:  " + newValue.getBrand());
                        labelModel.setText("model:  " + newValue.getModel());
                        labelSerialNumber.setText("serial number:  " + newValue.getSerialNumber());
                        labelDefect.setText("defect:  " + newValue.getDefect());
                        labelOwnerId.setText("owner id:  " + newValue.getOwnerId());
                        labelRepairId.setText("repair id:  " + newValue.getRepairId());
                        labelCompleteness.setText("completeness:  " + newValue.getCompleteness());
                        labelAppearance.setText("appearance:  " + newValue.getAppearance());
                    }
                    catch (NullPointerException npex) {
                        log.error(npex.getMessage());
                    }
                }
        );
    }
}
