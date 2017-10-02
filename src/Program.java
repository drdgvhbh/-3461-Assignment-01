import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;


public class Program extends Application {
    /**
     * Launches the application.
     * @param args none
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        try {
            FileInputStream fxmlStream = new FileInputStream(
                    new File(this.getClass().getClassLoader().getResource("fxml/Program.fxml").toURI()));
        } catch (NullPointerException e) {
            JSONLogger.err("File not found.", new Pair<>("File", "Program.fxml"));
            JSONLogger.info("Application is terminating.");
            return;
        }

    }
}
