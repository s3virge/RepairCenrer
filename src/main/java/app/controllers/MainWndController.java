package app.controllers;

import app.models.LoggedInUser;
import app.utils.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainWndController {
    private static final Logger log = LogManager.getLogger(MainWndController.class);
	private ScreenController screen = new ScreenController();

    @FXML
    private void initialize() {
        log.trace("");
        mLoggedInUser.setText(LoggedInUser.getLoggedInUser().getLogin());
        screen.loadFxml("/view/ReceivedDevicesPane.fxml", mainPain);
    }

    @FXML
    private Menu mLoggedInUser;

    @FXML
    private AnchorPane mainPain;

    /**
     * обработка нажатия на пункт меню Новый ремон
     */
    @FXML
    private void showReceiveNewDeviceDlg() {
        log.trace("");
        screen.loadDlgFxml("/view/dialogs/ReceiveDeviceDlg.fxml", "Оформить устройство");
        mainPain.getChildren().clear();
        showReceivedDevicesToday();
    }

    @FXML
    private void showReceivedDevicesToday() {
		screen.loadFxml("/view/ReceivedDevicesPane.fxml", mainPain);
    }

    @FXML
    private void showUserManagementDlg() {
        log.trace("");
		screen.loadDlgFxml("/view/dialogs/UserManagementDlg.fxml", "Пользователи");
    }

    @FXML
    private void showGiveOutDlg() {

    }

    @FXML
    private void showDevicesInRepair() {

    }

    @FXML
    private void showDevicesOnDiagnostics() {
        screen.loadFxml("/view/DevicesInDiagnosticsPane.fxml", mainPain);
    }

    @FXML
    private void logoff() {
        log.trace("");

        Parent layout = null;
        String sceneFile = "/view/loginWindow/LoginWnd.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();

        try {
            fxmlLoader.setLocation(getClass().getResource(sceneFile));
            layout = fxmlLoader.load();
        }
        catch (Exception ex) {
            log.error(ex);
        }

        //показываем окно ввода логина и пароля
        Scene scene = new Scene(layout, 360, 220);

        Stage primaryStage = ScreenController.getPrimaryStage();
        primaryStage.setTitle("A simple database of the service center");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //todo create give out device possibility
}