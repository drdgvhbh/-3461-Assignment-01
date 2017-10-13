package application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Data.SessionData;
import components.ImageBox.ImageBoxController;
import components.Model;
import components.ProgressBox.ProgressBoxController;
import components.PromptBox.PromptBoxController;
import components.StartButton.StartButtonController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import util.JSONLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


public class Program extends Application implements Initializable {
    @FXML
    private ImageBoxController imageBox1Controller, imageBox2Controller, imageBox3Controller, imageBox4Controller;

    @FXML
    private ProgressBoxController progressBox1Controller, progressBox2Controller, progressBox3Controller,
        progressBox4Controller, progressBox5Controller, progressBox6Controller, progressBox7Controller,
        progressBox8Controller, progressBox9Controller, progressBox10Controller;

    @FXML
    private StartButtonController startButtonController;

    @FXML
    private PromptBoxController promptBoxController;

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

        String currentFileName = "Program.fxml";

        // Load the FXML file and controllers and attach CSS files.
        try {
            root = FXMLLoader.load(getClass().getResource(currentFileName));
            currentFileName = "/resources/stylesheets/Program.css";
            root.getStylesheets().add(getClass().getResource(currentFileName).toExternalForm());

            currentFileName = "/components/ImageBox/ImageBox.fxml";
            Parent current = FXMLLoader.load(getClass().getResource(currentFileName));
            currentFileName = "/resources/stylesheets/ImageBox.css";
            current.getStylesheets().add(getClass().getResource(currentFileName).toExternalForm());
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
        final Model model = new Model();

        // Decide what happens at the end of each iteration
        model.iterationProperty().addListener((observable, oldValue, newValue) -> {
            // If we are resetting the iterations, do not do anything.
            if (newValue.equals(0)) {
                return;
            }
            if (newValue.intValue() >= Model.ITERATIONS_PER_PHASE) {
                // Lets change phases because phase one is over.
                if (model.getState() == Model.State.PHASE_1) {
                    model.setState(Model.State.PHASE_2);
                } else if (model.getState() == Model.State.PHASE_2) {
                    // Lets go back to the menu because phase two is over.
                    model.setState(Model.State.MENU);

                    // Lets write the results of our data to a file
                    buildOutputData(model);
                }

                // Reset our progress boxes so they look empty.
                for (Model.ProgressBoxId id : Model.ProgressBoxId.values()) {
                     model.getActiveProgressBoxes().get(id).setValue(false);
                }

                model.resetIterations();
                model.resetAnimalImages();
                model.resetAnimalNames();
            }
        });

        // Intialize the controllers after they have been injected.
        imageBox1Controller.initModel(model, Model.ImageBoxControllerId.TOP_LEFT);
        imageBox2Controller.initModel(model, Model.ImageBoxControllerId.TOP_RIGHT);
        imageBox3Controller.initModel(model, Model.ImageBoxControllerId.BOTTOM_LEFT);
        imageBox4Controller.initModel(model, Model.ImageBoxControllerId.BOTTOM_RIGHT);
        progressBox1Controller.initModel(model, Model.ProgressBoxId.ONE);
        progressBox2Controller.initModel(model, Model.ProgressBoxId.TWO);
        progressBox3Controller.initModel(model, Model.ProgressBoxId.THREE);
        progressBox4Controller.initModel(model, Model.ProgressBoxId.FOUR);
        progressBox5Controller.initModel(model, Model.ProgressBoxId.FIVE);
        progressBox6Controller.initModel(model, Model.ProgressBoxId.SIX);
        progressBox7Controller.initModel(model, Model.ProgressBoxId.SEVEN);
        progressBox8Controller.initModel(model, Model.ProgressBoxId.EIGHT);
        progressBox9Controller.initModel(model, Model.ProgressBoxId.NINE);
        progressBox10Controller.initModel(model, Model.ProgressBoxId.TEN);
        startButtonController.initModel(model);
        promptBoxController.initModel(model);

    }

    public void buildOutputData(Model model) {
        model.getSessionData().setDate(new Date());
        model.getSessionData().setTotalCorrectAnswersPercentage(model.getSessionData().getTotalCorrectAnswers()
            / SessionData.TOTAL_ANSWERS);
        model.getSessionData().setCorrectPhaseOneAnswersPercentage(model.getSessionData().getCorrectPhaseOneAnswers()
            / SessionData.TOTAL_S1_ANSWERS);
        model.getSessionData().setCorrectPhaseTwoAnswersPercentage(model.getSessionData().getCorrectPhaseTwoAnswers()
            / SessionData.TOTAL_S2_ANSWERS);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(model.getSessionData());
        try {

            FileWriter writer = new FileWriter(model.getSessionData().getDate().hashCode() + ".json");
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
