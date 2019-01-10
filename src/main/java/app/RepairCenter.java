package app;

import app.threads.DataBaseThread;
import app.utils.ScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
"Any fool can write code that a computer can understand.
Good programmers write code that humans can understand."
--- Martin Fowler, Refactoring: Improving the Design of Existing Code
*/

public class RepairCenter extends Application {

    private static final Logger logger = LogManager.getLogger(RepairCenter.class);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        logger.trace("");
        ScreenController.setPrimaryStage(primaryStage);

        //check if data base is exist
        new DataBaseThread("DB exist Thread");

        //показываем окно ввода логина и пароля.
        showLoginWindow();
    }

    private void showLoginWindow() {
        logger.trace("");

        Parent layout = null;
        String sceneFile = "/view/loginWindow/LoginWnd.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();

        try {
            fxmlLoader.setLocation(getClass().getResource(sceneFile));
            layout = fxmlLoader.load();
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }

        //показываем окно ввода логина и пароля
        Scene scene = new Scene(layout, 360, 220);

        Stage primaryStage = ScreenController.getPrimaryStage();
        primaryStage.setTitle("A simple database of the service center");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}