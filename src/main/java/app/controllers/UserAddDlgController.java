package app.controllers;

import app.dao.UserDao;
import app.models.User;
import app.utils.MsgBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.event.ActionEvent;

public class UserAddDlgController {
    private static Logger log = LogManager.getLogger(UserAddDlgController.class);
    @FXML
    TextField tfSurname;
@FXML
    TextField tfName;
@FXML
    TextField tfPatronymic;
@FXML
    TextField tfLogin;
@FXML
    TextField tfPassword;
    @FXML
    TextField tfPhoneNumber;
@FXML
    TextField tfEmail;

//    @FXML
//    private VBox vBoxFields;

    @FXML
    private void OnClickBtnAdd() {
        log.trace("");
        //todo add combobox with user groups

        User newUser = new User(tfLogin.getText(), tfPassword.getText(), "master",
                tfSurname.getText(), tfName.getText(), tfPatronymic.getText(),
                tfEmail.getText(), tfPhoneNumber.getText());

        UserDao.save(newUser);
    }

    @FXML
    private void OnClickBtnCancel(ActionEvent actionEvent){
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