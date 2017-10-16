package components.ImageBox;

import components.AbstractController;
import components.Data.IterationData;
import components.Model;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;
import util.JSONLogger;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Represents an image box controller.
 */
public class ImageBoxController extends AbstractController {
    /**
     * Represents this controller's identification value.
     */
    private Model.ImageBoxControllerId id;

    /**
     * Represents a button that displays an animal image.
     */
    @FXML
    private Button imageButton;

    /**
     * Represents a rectangular box to increase the visibility of {@link ImageBoxController#label}.
     */
    @FXML
    private ImageView labelBackground;

    /**
     * Represents a text message describing the animal image being displayed on this button.
     */
    @FXML
    private Text label;


    @Override
    public void initModel(Model model) throws IllegalStateException {
        initModel(model, null);

    }

    /**
     * Initializes the controller's dependencies with this application's {@code Model}.
     *
     * <p>Sets what happens when the button is pressed.</p>
     *
     * @param model The application state.
     * @param imageBoxId the id of this controller.
     * @throws IllegalStateException The model is already initialized.
     */
    public void initModel(Model model, Model.ImageBoxControllerId imageBoxId) throws IllegalStateException {
        super.initModel(model);
        this.id = imageBoxId;

        StringProperty imgURL = model.getCurrentAnimalImages().get(this.id);

        // If this button doesn't have an image, don't do anything.
        if (imgURL != null) {
            try {
                setButtonImage(imgURL.get());

                // Every time the image changes, change it on the button too.
                imgURL.addListener((observable, oldValue, newValue) -> setButtonImage(newValue));

                // Turn on our labels if we are in phase 2
                model.stateProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == Model.State.PHASE_2) {
                        labelBackground.setVisible(true);
                        label.setVisible(true);
                    } else {
                        labelBackground.setVisible(false);
                        label.setVisible(false);
                    }

                    // Starts the iteration cycle when the start button is pressed basically
                    // Even though we have four controllers, it should only fire once because the state will only change
                    // once
                    if (oldValue == Model.State.MENU && newValue == Model.State.PHASE_1) {
                        runIteration();
                    }


                });

            } catch (NullPointerException e) {
                JSONLogger.err("Invalid image url.", new Pair<>("URL", imgURL.get()));
            }
        }

        imageButton.setOnAction((event -> {
            // Don't let the user do anything if the application is paused (the blank out between iterations)
            if (model.getState() == Model.State.PAUSED) {
                return;
            }

            // Add data to our raw data trackers and continue to iterate
            if ((model.getState() == Model.State.PHASE_1 || model.getState() == Model.State.PHASE_2)
                    && !model.getCurrentAnimalNames().isEmpty()) {
                model.getCurrentIterationData().setChosenAnswer(model.getCurrentAnimalNames().get(this.id).get());
                if (model.getCurrentIterationData().getCorrectAnswer().equals(
                        model.getCurrentIterationData().getChosenAnswer())) {
                    model.getCurrentIterationData().setCorrect(true);
                }

                // Stop the timer that sends the user to the next iteration if no button is pressed
                if (model.getTimer() != null) {
                    model.getTimer().stop();
                }
                JSONLogger.info("User pressed button", new Pair<>("Image Box Id", this.id.toString()),
                    new Pair<>("Chosen Answer", model.getCurrentIterationData().getChosenAnswer()),
                    new Pair<>("Correct Answer", model.getCorrectAnimalName()));
                model.getSessionData().addIteration(model.getCurrentIterationData());
                model.increaseIteration();
                runIteration();
            }

        }));

        // We want improper labels, so we watch the state of these scrambled animal names and change the label text
        model.currentScrambledAnimalNamesProperty().addListener((observable, oldValue, newValue) -> {
            label.setText(newValue.get(id));
            if (model.getState() == Model.State.PHASE_2) {
                model.getCurrentIterationData().getLabels().put(this.id, newValue.get(id));
            }

        });

    }

    /**
     * Runs an iteration of the application.
     */
    private void runIteration() {
        if (model.getState() != Model.State.PHASE_1 && model.getState() != Model.State.PHASE_2) {
            return;
        }

        Model.State oldState = model.getState();
        model.setState(Model.State.PAUSED);
        model.resetAnimalImages();
        model.resetAnimalNames();

        // We paused the application for a small period of time so that the user will not accident press the answer
        // for the previous question if he runs out of time
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Model.TIME_BETWEEN_ITERATIONS), actionEvent -> {
            if (model.getState() != Model.State.PAUSED) {
                return;
            }
            model.setState(oldState);

            // Write to our raw data trackers
            model.setCurrentIterationData(new IterationData());
            model.getCurrentIterationData().setCurrentIteration(model.getIteration());
            model.getCurrentIterationData().setPhase(model.getState().toString());

            // Generate new names and images
            model.generateImageCombination(String.valueOf(model.getIteration() % 10));
            model.buildAnimalTree();
            Model.ImageBoxControllerId[] enumValues = Model.ImageBoxControllerId.values();
            Model.ImageBoxControllerId randomBox = enumValues[model.getRandom().nextInt(enumValues.length)];
            model.getCurrentIterationData().setCorrectAnswer(model.getCurrentAnimalNames().get(randomBox).get());
            model.setCorrectAnimalName(model.getAnimalTree().getGenericName(model.getCurrentAnimalNames().get(randomBox).get(),
                new ArrayList<>(model.getCurrentAnimalNames().values())));
            model.getCurrentIterationData().setTextPrompt(model.getCorrectAnimalName());
            model.setSimilarImageURL(model.getAnimalTree().getAnimalImages(model.getCorrectAnimalName()).get(0).toExternalForm());


            // Show the user his iteration progress
            model.getActiveProgressBoxes().get(Model.ProgressBoxId.values()[(model.getIteration() % 10)]).setValue(true);

        }));
        timeline.play();

        // Start a timer to go to the next iteration if the user runs out of time
        model.setTimer(new Timeline(new KeyFrame(Duration.millis(
            Model.TIME_BETWEEN_ITERATIONS + Model.ITERATION_TIME_LIMIT), actionEvent -> {
            if (model.getState() == Model.State.MENU) {
                return;
            }

            model.getCurrentIterationData().setChosenAnswer("DNA");
            model.getCurrentIterationData().setCorrect(false);
            model.getSessionData().addIteration(model.getCurrentIterationData());
            model.increaseIteration();
            runIteration();
        })));
        model.getTimer().play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Sets the background of this button.
     * @param url the image <code>URL</code>
     */
    private void setButtonImage(String url) {
        Background background = new Background(
            new BackgroundImage(
                new Image(
                    url,
                    imageButton.getPrefWidth(), imageButton.getPrefHeight(),
                    true,
                    true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
            )
        );
        imageButton.setBackground(background);
    }

}
