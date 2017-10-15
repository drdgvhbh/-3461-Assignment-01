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

    public void initModel(Model model, Model.ImageBoxControllerId imageBoxId) throws IllegalStateException {
        super.initModel(model);
        this.id = imageBoxId;

        StringProperty imgURL = model.getCurrentAnimalImages().get(this.id);

        if (imgURL != null) {
            try {
                setButtonImage(imgURL.get());
                imgURL.addListener((observable, oldValue, newValue) -> setButtonImage(newValue));

                model.stateProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == Model.State.PHASE_2) {
                        labelBackground.setVisible(true);
                        label.setVisible(true);
                    } else {
                        labelBackground.setVisible(false);
                        label.setVisible(false);
                    }

                    if (oldValue == Model.State.MENU && newValue == Model.State.PHASE_1) {
                        runIteration();
                    }


                });

            } catch (NullPointerException e) {
                JSONLogger.err("Invalid image url.", new Pair<>("URL", imgURL.get()));
            }
        }

        imageButton.setOnAction((event -> {
            if (model.getState() == Model.State.PAUSED) {
                return;
            }
            if ((model.getState() == Model.State.PHASE_1 || model.getState() == Model.State.PHASE_2)
                    && !model.getCurrentAnimalNames().isEmpty()) {
                model.getCurrentIterationData().setChosenAnswer(model.getCurrentAnimalNames().get(this.id).get());
                if (model.getCurrentIterationData().getCorrectAnswer().equals(
                        model.getCurrentIterationData().getChosenAnswer())) {
                    model.getCurrentIterationData().setCorrect(true);
                }
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

        model.currentScrambledAnimalNamesProperty().addListener((observable, oldValue, newValue) -> {
            label.setText(newValue.get(id));
            if (model.getState() == Model.State.PHASE_2) {
                model.getCurrentIterationData().getLabels().put(this.id, newValue.get(id));
            }

        });

    }

    private void runIteration() {
        if (model.getState() != Model.State.PHASE_1 && model.getState() != Model.State.PHASE_2) {
            return;
        }
        Model.State oldState = model.getState();
        model.setState(Model.State.PAUSED);
        model.resetAnimalImages();
        model.resetAnimalNames();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Model.TIME_BETWEEN_ITERATIONS), actionEvent -> {
            if (model.getState() != Model.State.PAUSED) {
                return;
            }
            model.setState(oldState);
            model.setCurrentIterationData(new IterationData());
            model.getCurrentIterationData().setCurrentIteration(model.getIteration());
            model.getCurrentIterationData().setPhase(model.getState().toString());

            model.generateImageCombination(String.valueOf(model.getIteration() % 10));
            model.buildAnimalTree();
            Model.ImageBoxControllerId[] enumValues = Model.ImageBoxControllerId.values();
            Model.ImageBoxControllerId randomBox = enumValues[model.getRandom().nextInt(enumValues.length)];
            model.getCurrentIterationData().setCorrectAnswer(model.getCurrentAnimalNames().get(randomBox).get());
            model.setCorrectAnimalName(model.getAnimalTree().getGenericName(model.getCurrentAnimalNames().get(randomBox).get(),
                new ArrayList<>(model.getCurrentAnimalNames().values())));
            model.getCurrentIterationData().setTextPrompt(model.getCorrectAnimalName());
            model.setSimilarImageURL(model.getAnimalTree().getAnimalImages(model.getCorrectAnimalName()).get(0).toExternalForm());


            model.getActiveProgressBoxes().get(Model.ProgressBoxId.values()[(model.getIteration() % 10)]).setValue(true);

        }));
        timeline.play();

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
