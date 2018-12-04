import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MsgBox;

public class RepairCenter extends Application {

    private static final Logger logger = LoggerFactory.getLogger(RepairCenter.class);

    private Stage primaryStage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        logger.info("logger info is here.");

        MsgBox.show();
    }
}