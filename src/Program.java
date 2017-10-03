import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import util.JSONLogger;

import java.net.URL;


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
        Parent root;
        String currentFileName = "components/Program.fxml";
        try {
            URL fxmlFile = getClass().getResource(currentFileName);
            root = FXMLLoader.load(fxmlFile);
        } catch (NullPointerException e) {
            JSONLogger.err("File not found.", new Pair<>("File", currentFileName));
            JSONLogger.info("Application is terminating.");
            return;
        }

        currentFileName = "resources/stylesheets/Program.css";
        try {
            root.getStylesheets().add(getClass().getResource(currentFileName).toExternalForm());
        } catch (NullPointerException e) {
            JSONLogger.warn("File not found.", new Pair<>("File", currentFileName));
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
