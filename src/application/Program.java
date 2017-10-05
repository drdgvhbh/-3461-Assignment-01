package application;

import components.ImageBox.ImageBoxController;
import components.Model;
import components.StartButton.StartButtonController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import util.JSONLogger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Program extends Application implements Initializable {
    @FXML
    private StackPane imageBox1, imageBox2, imageBox3, imageBox4;

    @FXML
    private ImageBoxController imageBox1Controller, imageBox2Controller, imageBox3Controller, imageBox4Controller;

    @FXML
    private StartButtonController startButtonController;

    /**
     * Launches the application.
     *
     * @param args none
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root;
        FXMLLoader loader;

        String currentFileName = "Program.fxml";

        // Load the FXML file and controllers.
        try {
            root = FXMLLoader.load(getClass().getResource(currentFileName));

            currentFileName = "../components/ImageBox/ImageBox.fxml";
        } catch (NullPointerException e) {
            JSONLogger.err("File not found.", new Pair<>("File", currentFileName));
            JSONLogger.info("Application is terminating.");
            return;
        } catch (IOException e) {
            JSONLogger.err("Error parsing file.", new Pair<>("File", currentFileName));
            JSONLogger.info("Application is terminating.");
            return;
        }

        currentFileName = "../resources/stylesheets/Program.css";

        // Attach CSS
        try {
            root.getStylesheets().add(getClass().getResource(currentFileName).toExternalForm());
        } catch (NullPointerException e) {
            JSONLogger.warn("File not found.", new Pair<>("File", currentFileName));
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model model = new Model();

        // Intialize the controllers after they have been injected.
        imageBox1.setId("1");
        imageBox1Controller.initModel(model);
        imageBox2Controller.initModel(model);
        imageBox3Controller.initModel(model);
        imageBox4Controller.initModel(model);

        startButtonController.initModel(model);

    }
}
