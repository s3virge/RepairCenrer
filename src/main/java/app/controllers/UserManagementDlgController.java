package app.controllers;

import app.dao.UserDao;
import app.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Vector;

public class UserManagementDlgController {
    private static Logger log = LogManager.getLogger("UserManagementDlgController");

    @FXML
    private TableView tvUserInfo;

    @FXML
    private TableColumn colSurname;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colPatronymic;
    @FXML
    private TableColumn colLogin;
    @FXML
    private TableColumn colPassword;
    @FXML
    private TableColumn colEmail;

    //todo add another columns

    @FXML
    private Button btnAddUser;

    private ObservableList<User> tvObservableList = FXCollections.observableArrayList();

    @FXML
    private void onBtnCancelClick(ActionEvent event) {
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

        fillTable();
    }

    private void fillTable() {
        final Vector<User> list = UserDao.getList();
        tvObservableList.clear();

        for (User u : list) {
            tvObservableList.add(u);
        }

        tvUserInfo.setItems(tvObservableList);
    }


    @FXML
    private void OnAddBtn() {
        log.trace("");
        // Загружаем fxml-файл и создаём новую сцену
        // для всплывающего диалогового окна.
        FXMLLoader loader = new FXMLLoader();
        //Sets the location used to resolve relative path attribute values.
        //getResource - Finds a resource with a given name.
        URL resource = getClass().getResource("/view/dialogs/UserAddDlg.fxml");
        loader.setLocation(resource);

        Parent layout = null;

        try {
            layout = loader.load();
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }

        // Создаём подмостки для диалогового окна.
        Stage dialogStage = new Stage();
        //подготавливаем их
        dialogStage.setTitle("Добавить пользователя");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        final Window window = btnAddUser.getScene().getWindow();
        dialogStage.initOwner(window);

        //расставляем декорации на сцене согласно плану
        Scene scene = new Scene(layout);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();

        fillTable();
    }
}
