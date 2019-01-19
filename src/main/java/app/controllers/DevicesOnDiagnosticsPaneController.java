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
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DevicesOnDiagnosticsPaneController {

    @FXML
    private ListView lstDeviceList;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfBrand;
    @FXML
    private TextField tfModel;
    @FXML
    private TextField tfSerialNumber;
    @FXML
    private TextField tfDefect;
    @FXML
    private TextField tfOwnerId;
    @FXML
    private TextField tfRepairId;
    @FXML
    private TextField tfCompleteness;
    @FXML
    private TextField tfAppearance;

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
            tfId.setText("");
            tfType.setText("");
            tfBrand.setText("");
            tfModel.setText("");
            tfSerialNumber.setText("");
            tfDefect.setText("");
            tfOwnerId.setText("");
            tfRepairId.setText("");
            tfCompleteness.setText("");
            tfAppearance.setText("");
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
                        tfId.setText("device id: " + newValue.getId());
                        tfType.setText("type:  " + newValue.getType());
                        tfBrand.setText("brand:  " + newValue.getBrand());
                        tfModel.setText("model:  " + newValue.getModel());
                        tfSerialNumber.setText("serial number:  " + newValue.getSerialNumber());
                        tfDefect.setText("defect:  " + newValue.getDefect());
                        tfOwnerId.setText("owner id:  " + newValue.getOwnerId());
                        tfRepairId.setText("repair id:  " + newValue.getRepairId());
                        tfCompleteness.setText("completeness:  " + newValue.getCompleteness());
                        tfAppearance.setText("appearance:  " + newValue.getAppearance());
                    }
                    catch (NullPointerException npex) {
                        log.error(npex.getMessage());
                    }
                }
        );
    }
}
