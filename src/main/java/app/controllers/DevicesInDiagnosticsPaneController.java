package app.controllers;

import app.dao.DeviceDao;
import app.dao.handbooks.repair.RepairDao;
import app.models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DevicesInDiagnosticsPaneController {

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
    @FXML
    private Pane tilePane;
    @FXML
    private TextArea taMasterComments;
    @FXML
    private TextArea taDiagnosticResult;

    private int repairId;
    private int observListIndex = 0;

    private ObservableList<DeviceInDiagnostics> observDeviceList = FXCollections.observableArrayList();

//    private final String today = gerCurrentDate();

    private static final Logger log = LogManager.getLogger(DevicesInDiagnosticsPaneController.class);

    @FXML
    private void initialize() {
        log.trace("");
        updateDeviceListView(false);
        addDeviceListListener();
        addTextAreaListeners();
        lstDeviceList.getSelectionModel().selectFirst();
    }

    private void updateDeviceListView(boolean selectLast) {
        int selectedItem = lstDeviceList.getSelectionModel().getSelectedIndex();

        try {
            List devices = DeviceDao.select(DeviceStatus.DIAGNOSTICS, LoggedInUser.getLoggedInUser().getLogin());

			observDeviceList.clear();
            clearFields(diagnosticsPane);
            clearFields(tilePane);
            observDeviceList.addAll(devices);
            lstDeviceList.setItems(observDeviceList);

            if (selectLast) {
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
                    ((TextField) node).setText("");
                }
                else if (node instanceof Label) {
                    ((Label) node).setText("");
                }
                else if (node instanceof TextArea) {
                    ((TextArea) node).setText("");
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
                (ChangeListener<DeviceInDiagnostics>) (observable, oldValue, newValue) ->
                {
                    try {
//                        tfId.setText("device id: " + newValue.getId());
                        repairId = newValue.getDevice().getId();
                        tfType.setText(newValue.getDevice().getType());
                        tfBrandModel.setText(newValue.getDevice().getBrand() + " " + newValue.getDevice().getModel());
                        tfSerialNumber.setText(newValue.getDevice().getSerialNumber());
                        tfDefect.setText(newValue.getDevice().getDefect());
                        tfCompleteness.setText(newValue.getDevice().getCompleteness());
                        tfAppearance.setText(newValue.getDevice().getAppearance());

                        taMasterComments.setText(newValue.getRepair().getMasterComments());
                        taDiagnosticResult.setText(newValue.getRepair().getDiagnosticResult());

                        DeviceInDiagnostics deviceInDiagnostics = observable.getValue();
                        observListIndex = observDeviceList.indexOf(deviceInDiagnostics);

//                        log.debug("indexOf(deviceInDiagnostics) = {}", observListIndex);
                    }
                    catch (NullPointerException npex) {
                        log.error(npex.getMessage());
                    }
                }
        );
    }

    private void addTextAreaListeners() {
        taMasterComments.focusedProperty().addListener(
                new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                        if (!newPropertyValue) {
                            //writes new text to database and change value in observable list
                            log.trace("taMasterComments lose focus");
                            RepairDao.updateMasterComments(repairId, taMasterComments.getText());

                            DeviceInDiagnostics devInDiagnostics = observDeviceList.get(observListIndex);
                            devInDiagnostics.getRepair().setMasterComments(taMasterComments.getText());
                            observDeviceList.set(observListIndex, devInDiagnostics);
                        }
                    }
                }
        );

        taDiagnosticResult.focusedProperty().addListener(
                new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                        if (newPropertyValue) {
//                            System.out.println("Textfield on focus");
                        }
                        else {
//                            System.out.println("Textfield out focus");
                            log.trace("taDiagnosticResult lose focus");
                            RepairDao.updateDiagnosticResult(repairId, taDiagnosticResult.getText());

                            DeviceInDiagnostics devInDiagnostics = observDeviceList.get(observListIndex);
                            devInDiagnostics.getRepair().setDiagnosticResult(taDiagnosticResult.getText());
                            observDeviceList.set(observListIndex, devInDiagnostics);
                        }
                    }
                }
        );
    }

    @FXML
    private void onBtnReady() {
//        JOptionPane.showMessageDialog(null, "change devise status to Ready");
        RepairDao.updateStatus(repairId, DeviceStatus.READY);

        updateDeviceListView(false);
    }
}
