package app.controllers;

import app.dao.UserDao;
import app.models.User;
import app.models.UserGroup;
import app.utils.MD5Hash;
import app.utils.MsgBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.event.ActionEvent;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static app.utils.MsgBox.Type.MB_ERROR;

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

    @FXML
    private ComboBox cbUserGroup;

    @FXML
    private void OnClickBtnAdd(ActionEvent event) {
        log.trace("");

        String userGroup = (String) cbUserGroup.getSelectionModel().getSelectedItem();

        String paswd = null;

        try {
            paswd = MD5Hash.get(tfPassword.getText());
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
//            MsgBox.show(e.getMessage(), MB_ERROR);
        }

        User newUser = new User(0, tfLogin.getText(), paswd, userGroup,
                tfSurname.getText(), tfName.getText(), tfPatronymic.getText(),
                tfEmail.getText(), tfPhoneNumber.getText());

//        log.debug("{}", newUser.toString());

        UserDao.insert(newUser);

        closeWnd(event);
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

        cbUserGroup.getItems().addAll(UserGroup.ADMIN, UserGroup.ACCEPTOR, UserGroup.MANAGER, UserGroup.MASTER);
    }
}
