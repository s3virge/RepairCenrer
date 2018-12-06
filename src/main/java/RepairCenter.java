import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.MsgBox;

public class RepairCenter extends Application {

    private static final Logger logger = LogManager.getLogger(RepairCenter.class);

    private Stage primaryStage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        logger.info("logger info is here.");
        logger.debug("primaryStage object {}", primaryStage);

        MsgBox.show();
    }
}