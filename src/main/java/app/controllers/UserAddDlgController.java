package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.event.ActionEvent;

public class UserAddDlgController {
    private static Logger log = LogManager.getLogger(UserAddDlgController.class);

    @FXML
    private void OnBtnAddClick() {
        log.trace("");
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
}
