package application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Data.DataAnalyzer;
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


/**
 * Represents the entry point for the application.
 */
public class Program extends Application implements Initializable {
    /**
     * Controller for the top left image button.
     */
    @FXML
    private ImageBoxController imageBox1Controller;

    /**
     * Controller for the top right image button.
     */
    @FXML
    private ImageBoxController imageBox2Controller;

    /**
     * Controller for the bottom left image button.
     */
    @FXML
    private ImageBoxController imageBox3Controller;

    /**
     * Controller for the bottom right image button.
     */
    @FXML
    private ImageBoxController imageBox4Controller;

    /**
     * Controller for the first progress box.
     */
    @FXML
    private ProgressBoxController progressBox1Controller;

    /**
     * Controller for the second progress box.
     */
    @FXML
    private ProgressBoxController progressBox2Controller;

    /**
     * Controller for the third progress box.
     */
    @FXML
    private ProgressBoxController progressBox3Controller;

    /**
     * Controller for the fourth progress box.
     */
    @FXML
    private ProgressBoxController progressBox4Controller;

    /**
     * Controller for the fifth progress box.
     */
    @FXML
    private ProgressBoxController progressBox5Controller;

    /**
     * Controller for the sixth progress box.
     */
    @FXML
    private ProgressBoxController progressBox6Controller;

    /**
     * Controller for the seventh progress box.
     */
    @FXML
    private ProgressBoxController progressBox7Controller;

    /**
     * Controller for the eight progress box.
     */
    @FXML
    private ProgressBoxController progressBox8Controller;

    /**
     * Controller for the ninth progress box.
     */
    @FXML
    private ProgressBoxController progressBox9Controller;

    /**
     * Controller for the tenth progress box.
     */
    @FXML
    private ProgressBoxController progressBox10Controller;

    /**
     * Controller for the start button.
     */
    @FXML
    private StartButtonController startButtonController;

    /**
     * Controller for the text prompt and image prompt.
     */
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

    /**
     * {@inheritDoc}
     *
     * <p>Loads all stylesheets and FXML documents.</p>
     *
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root;

        String currentFileName = "Program.fxml";

        // Load FXML files and attach CSS files.
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
        JSONLogger.info("Application started");

        // Unnecessary unless were doing analysis
        /*generateDataAnalysis();*/

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

                // Reset everything else
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

        JSONLogger.info("Components initialized");

        // Log a state chance
        model.stateProperty().addListener((observable, oldValue, newValue) -> {
            JSONLogger.info("State Change", new Pair<>("Old State", oldValue.toString()),
                new Pair<>("New State", newValue.toString()));
        });

    }

    /**
     * Writes the session data to a JSON file.
     * @param model The application model.
     */
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

        JSONLogger.info("Output file created", new Pair<>("File",
            model.getSessionData().getDate().hashCode() + ".json"));
    }

    /**
     * Extracts raw data from files produced by {@link Program#buildOutputData(Model)} from /resources/results/
     * and generates an analysis.
     */
    public void generateDataAnalysis() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        DataAnalyzer analyzer = new DataAnalyzer();
        String json = gson.toJson(new DataAnalyzer());
        try {

            FileWriter writer = new FileWriter("Analysis" + analyzer.hashCode() + ".json");
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONLogger.info("Output file created", new Pair<>("File",
            "Analysis" + analyzer.hashCode() + ".json"));
    }
}
