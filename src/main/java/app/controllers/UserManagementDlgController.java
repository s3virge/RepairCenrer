package app.controllers;

import app.dao.UserDao;
import app.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Vector;

public class UserManagementDlgController {

    @FXML
    TableView tvUserInfo;

    @FXML
    TableColumn colSurname;
    @FXML
    TableColumn colName;
    @FXML
    TableColumn colPatronymic;
    @FXML
    TableColumn colLogin;
    @FXML
    TableColumn colPassword;
    @FXML
    TableColumn colEmail;

    private ObservableList<User> tvObservableList = FXCollections.observableArrayList();

    @FXML
    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        colSurname.setCellValueFactory(new PropertyValueFactory("surname"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colPatronymic.setCellValueFactory(new PropertyValueFactory("patronymic"));
        colLogin.setCellValueFactory(new PropertyValueFactory("login"));
        colPassword.setCellValueFactory(new PropertyValueFactory("password"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));

        final Vector<User> list = UserDao.getList();

        for (User u : list) {
            tvObservableList.add(u);
        };

        tvUserInfo.setItems(tvObservableList);
    }
}
