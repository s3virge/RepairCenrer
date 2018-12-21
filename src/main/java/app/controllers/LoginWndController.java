package app.controllers;

import app.RepairCenter;
import app.dao.UserDao;
import app.models.LoggedInUser;
import app.models.User;
import app.utils.MD5Hash;
import app.utils.MsgBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static app.utils.MsgBox.Type.MB_ERROR;

public class LoginWndController {
    private static final Logger logger = LogManager.getLogger(LoginWndController.class);

    @FXML private Text errorLabel;
    @FXML private TextField loginField;
    @FXML private TextField passwordField;

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        setDefaultTextFieldValue();
    }

    private void setDefaultTextFieldValue() {
        loginField.setText("admin");
        passwordField.setText("admin");
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        logger.trace("");

        if(isInputValid()) {

            String login = loginField.getText();
            String paswd = null;

            try {
                paswd = MD5Hash.get(passwordField.getText());
            }
            catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
                MsgBox.show(e.getMessage(), MB_ERROR);
            }

            UserDao userDao = new UserDao();
            User user = userDao.getUserByLogin(login);

            //если юзера нет
            if (user.isEmpty()) {
                showLoginError();
                return;
            }

            LoggedInUser.setLoggedInUser(user);

            if (user.getPassword().equals(paswd)) {
                //главное окно для разных групп пользователь буде отображаться по разному.
                //нужно знать группу пользователя который залогинился.

                switch (user.getGroup()) {
                    case "administrator":
                        showMainWnd();
                        break;

                    case "acceptor":
                        showMainWnd();
                        break;

                    case "master":
                        showMainWnd();
                        break;
                }
            }
            else {
                //показать сообщение с ошибкой
                showLoginError();
            }
        }
    }

    private boolean isInputValid() {

        if (loginField.getText() == null || loginField.getText().length() == 0) {
            errorLabel.setText("Login can not be empty!");
            loginField.requestFocus();
            return false;
        }

        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorLabel.setText("Password can not be empty!");
            passwordField.requestFocus();
            return false;
        }

        errorLabel.setText("");
        return true;
    }

    private void showMainWnd() {
        logger.trace("");

        Stage stage = new RepairCenter().getPrimaryStage(); //mainApp.getPrimaryStage();

        Parent mainWndLayout = null;
        //Поскольку имя начинается с символа '/' – оно считается абсолютным. Без / - считается относительным
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainWindow/MainWindow.fxml"));

        try {
            mainWndLayout = fxmlLoader.load();
        }
        catch ( Exception ex ) {
            logger.error(ex.getMessage());
        }

        stage.setTitle("A simple database of the service center");
        stage.setScene(new Scene(mainWndLayout));
        stage.centerOnScreen();
        //mainWindow.setFullScreen(true);
        stage.show();
    }

    private void showLoginError(){
        MsgBox.show("Такой комбинации логина и пароля не существует.");
        loginField.requestFocus();
        passwordField.setText("");
    }
}