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
    public void start(Stage primaryStage) throws IOException {
        Parent root;
        FXMLLoader loader;

        String currentFileName = "Program.fxml";

        // Load the FXML file and controllers and attach CSS files.
        try {
            root = FXMLLoader.load(getClass().getResource(currentFileName));
            currentFileName = "../resources/stylesheets/Program.css";
            root.getStylesheets().add(getClass().getResource(currentFileName).toExternalForm());

            currentFileName = "../components/ImageBox/ImageBox.fxml";
            Parent current = FXMLLoader.load(getClass().getResource(currentFileName));
            currentFileName = "../resources/stylesheets/ImageBox.css";
            current.getStylesheets().add(getClass().getResource(currentFileName).toExternalForm());

            currentFileName = "../components/ImageBox/ImageBox.fxml";
        } catch (NullPointerException e) {
            JSONLogger.err("File not found.", new Pair<>("File", currentFileName));
            JSONLogger.info("Application is terminating.");
            return;
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model model = null;
        model = new Model();

        // Intialize the controllers after they have been injected.
        imageBox1Controller.initModel(model, Model.ImageBoxId.TOP_LEFT);
        imageBox2Controller.initModel(model, Model.ImageBoxId.TOP_RIGHT);
        imageBox3Controller.initModel(model, Model.ImageBoxId.BOTTOM_LEFT);
        imageBox4Controller.initModel(model, Model.ImageBoxId.BOTTOM_RIGHT);

        startButtonController.initModel(model);

    }
}
