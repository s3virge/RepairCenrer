package app.controllers;

import app.dao.DeviceDao;
import app.models.Device;
import app.models.DeviceStatus;
import app.models.LoggedInUser;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DevicesOnDiagnosticsPaneController {

    @FXML
    private ListView lstDeviceList;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfBrandModel;
    @FXML
    private TextField tfSerialNumber;
    @FXML
    private TextField tfDefect;
    @FXML
    private TextField tfCompleteness;
    @FXML
    private TextField tfAppearance;
    @FXML
    private Pane diagnosticsPane;

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

            for (Node node : diagnosticsPane.getChildren()) {
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
//                        tfId.setText("device id: " + newValue.getId());
                       tfType.setText(newValue.getType());
                       tfBrandModel.setText(newValue.getBrand() +" "+newValue.getModel());
                       tfSerialNumber.setText(newValue.getSerialNumber());
                       tfDefect.setText(newValue.getDefect());
                       tfCompleteness.setText(newValue.getCompleteness());
                       tfAppearance.setText(newValue.getAppearance());
                    }
                    catch (NullPointerException npex) {
                        log.error(npex.getMessage());
                    }
                }
        );
    }
}
