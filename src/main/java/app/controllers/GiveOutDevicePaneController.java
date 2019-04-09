package app.controllers;

import app.models.Device;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    }
}