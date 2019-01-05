package app.controllers;

import app.dao.UserDao;
import app.models.User;
import app.models.UserGroup;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public class UserEditDlgController {
    private static Logger log = LogManager.getLogger(UserEditDlgController.class);

    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPatronymic;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfEmail;

    @FXML
    private ComboBox cbUserGroup;
    @FXML
    private Button btnEditPassword;

    private int userId;

    @FXML
    private void OnClickBtnEdit(ActionEvent event) {
        log.trace("");

        String userGroup = (String) cbUserGroup.getSelectionModel().getSelectedItem();

        User userToChange = new User(userId, tfLogin.getText(), tfPassword.getText(), userGroup,
                tfSurname.getText(), tfName.getText(), tfPatronymic.getText(),
                tfEmail.getText(), tfPhoneNumber.getText());

//        log.debug("{}", newUser.toString());

        UserDao.update(userToChange);

        closeWnd(event);
    }

    @FXML
    private void OnClickBtnCancel(ActionEvent actionEvent) {
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
        log.trace("");

//        tfSurname.requestFocus(); //did not working
        //this trick is working
        Platform.runLater(() -> tfSurname.requestFocus());

        cbUserGroup.getItems().addAll(UserGroup.ADMIN, UserGroup.ACCEPTOR, UserGroup.MANAGER, UserGroup.MASTER);
    }

    public void fillTextfields(User selectedUser) {

        userId = selectedUser.getId();
        tfSurname.setText(selectedUser.getSurname());
        tfName.setText(selectedUser.getName());
        tfPatronymic.setText(selectedUser.getPatronymic());
        tfLogin.setText(selectedUser.getLogin());
        tfPassword.setText(selectedUser.getPassword());
        tfPhoneNumber.setText(selectedUser.getPhoneNumber());
        tfEmail.setText(selectedUser.getEmail());

        cbUserGroup.getSelectionModel().select(selectedUser.getGroup());
    }

    @FXML
    private void onClickBtnEditPassword() {
        log.trace("");
        // Загружаем fxml-файл и создаём новую сцену
        // для всплывающего диалогового окна.
        FXMLLoader loader = new FXMLLoader();
        //Sets the location used to resolve relative path attribute values.
        //getResource - Finds a resource with a given name.
        URL resource = getClass().getResource("/view/dialogs/ChangePasswordDlg.fxml");
        loader.setLocation(resource);

        Parent layout = null;

        try {
            layout = loader.load();
        }
        catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        // Создаём подмостки для диалогового окна.
        Stage dialogStage = new Stage();
        //подготавливаем их
        dialogStage.setTitle("Изменить пароль");
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        final Window window = btnEditPassword.getScene().getWindow();
        dialogStage.initOwner(window);

        //расставляем декорации на сцене согласно плану
        Scene scene = new Scene(layout);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();

        ChangePasswordDlgController changePasswordDlgController = loader.getController();
        if (changePasswordDlgController.okBtnPressed()){
            tfPassword.setText(changePasswordDlgController.getNewPassword());
        }
    }
}
