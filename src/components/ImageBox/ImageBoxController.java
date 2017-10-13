package components.ImageBox;

import components.AbstractController;
import components.Iteration;
import components.Model;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Pair;
import util.JSONLogger;

import java.net.URL;
import java.util.ResourceBundle;

public class ImageBoxController extends AbstractController {
    private Model model;

    private Model.ImageBoxControllerId id;

    @FXML
    private Button imageButton;

    @FXML
    private ImageView labelBackground;

    @FXML
    private Text label;

    private ChangeListener<String> imageButtonListener;

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
                imgURL.addListener(imageButtonListener);

                model.stateProperty().addListener((observable, oldValue, newValue) -> {
                    if (oldValue == newValue) {
                        return;
                    }


                    if (newValue == Model.State.PHASE_2) {
                        labelBackground.setVisible(true);
                        label.setVisible(true);
                    } else {
                        labelBackground.setVisible(false);
                        label.setVisible(false);
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
                model.getSessionData().addIteration(model.getCurrentIterationData());
                model.increaseIteration();
                Iteration.run(model);
            }

        }));

        model.currentScrambledAnimalNamesProperty().addListener((observable, oldValue, newValue) -> {
            label.setText(newValue.get(id));
            if (model.getState() == Model.State.PHASE_2) {
                model.getCurrentIterationData().getLabels().put(this.id, newValue.get(id));
            }

        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageButtonListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setButtonImage(newValue);
            }
        };
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
