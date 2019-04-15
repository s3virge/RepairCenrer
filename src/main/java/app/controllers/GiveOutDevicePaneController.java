package app.controllers;

import app.dao.DeviceDao;
import app.dao.handbooks.repair.RepairDao;
import app.exceptions.DeviceDaoException;
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
    private Label diagnosticsResult;
    @FXML
    private Label repairResult;

    @FXML
    private Pane labelsPane; //нужна что-бы перебирать все дочерние окна

    //list will contains device id
    private ObservableList<Integer> observDeviceList = FXCollections.observableArrayList();
    private DeviceAndHisRepair currentDeviceAndHisRepair = null;

    private static final Logger log = LogManager.getLogger(GiveOutDevicePaneController.class);

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
            Collection<Integer> devicesIds = DeviceDao.selectIdsByStatus(DeviceStatus.READY);
            observDeviceList.clear();
            clearFields(labelsPane);

            if (devicesIds.isEmpty()) {
                log.trace("list of devices ids is empty");
                return;
            }

            observDeviceList.addAll(devicesIds);
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
                    ((TextField) node).setText("");
                }
                else if (node instanceof Label) {
                    // clear
                    ((Label) node).setText("");
                }
            }
        }
        catch (NullPointerException npex) {
            log.error(npex.getMessage());
        }
    }

    private void addDeviceListListener() {
        lstDeviceList.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<Integer>) (observable, oldValue, newValue) ->
                {
                    if (newValue != null) {
                        try {
                            currentDeviceAndHisRepair = DeviceDao.selectById(newValue);

                            label.setText("device id: " + currentDeviceAndHisRepair.getDevice().getId());
                            label1.setText("type:  " + currentDeviceAndHisRepair.getDevice().getType());
                            label2.setText("brand:  " + currentDeviceAndHisRepair.getDevice().getBrand());
                            label3.setText("model:  " + currentDeviceAndHisRepair.getDevice().getModel());
                            label4.setText("serial number:  " + currentDeviceAndHisRepair.getDevice().getSerialNumber());
                            label5.setText("defect:  " + currentDeviceAndHisRepair.getDevice().getDefect());
                            label6.setText("owner id:  " + currentDeviceAndHisRepair.getDevice().getOwnerId());
                            label7.setText("repair id:  " + currentDeviceAndHisRepair.getRepair().getId());
                            label8.setText("completeness:  " + currentDeviceAndHisRepair.getDevice().getCompleteness());
                            label9.setText("appearance:  " + currentDeviceAndHisRepair.getDevice().getAppearance());

                            diagnosticsResult.setText(currentDeviceAndHisRepair.getRepair().getDiagnosticResult());
                            repairResult.setText(currentDeviceAndHisRepair.getRepair().getRepairResult());

                            log.debug("list listener newValue -> {}", newValue);
                        }
                        catch (NullPointerException | DeviceDaoException ex) {
                            log.error(ex.getMessage());
                        }
                    }
                }
        );
    }

    @FXML
    public void onBtnClickGiveOut() {
        RepairDao.updateDeviceStatus(currentDeviceAndHisRepair.getRepair().getId(), DeviceStatus.GIVEN_BACK);
        updateDeviceListView(true);
    }
}