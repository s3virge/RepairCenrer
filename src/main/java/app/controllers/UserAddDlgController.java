package app.controllers;

import app.utils.MsgBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.event.ActionEvent;

public class UserAddDlgController {
    private static Logger log = LogManager.getLogger(UserAddDlgController.class);
    @FXML
    TextField tfSurname;

    @FXML
    private void OnBtnAddClick() {
        log.trace("");
        //todo save new user to database
    }

    @FXML
    private void OnBtnCancelClick(ActionEvent actionEvent){
       closeWnd(actionEvent);
    }

    private void closeWnd(ActionEvent actionEvent) {
        //получить источник события
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() {
//        tfSurname.requestFocus(); //did not working
        //this trick is working
        Platform.runLater(()->tfSurname.requestFocus());
    }
}