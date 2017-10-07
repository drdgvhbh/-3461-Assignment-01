package components.ImageBox;

import components.AbstractController;
import components.AnimalTree.AnimalNode;
import components.Model;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Pair;
import util.JSONLogger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

public class ImageBoxController extends AbstractController {
    private Model model;

    private Model.ImageBoxId id;

    @FXML
    private Button imageButton;

    @Override
    public void initModel(Model model) throws IllegalStateException {
        initModel(model, null);

    }

    public void initModel(Model model, Model.ImageBoxId imageBoxId) throws IllegalStateException {
        super.initModel(model);
        this.id = imageBoxId;

        StringProperty imgURL = model.getCurrentAnimals().get(this.id);
        if (imgURL != null) {
            try {
                Background background = new Background(
                        new BackgroundImage(
                                new Image(
                                getClass().getResource(imgURL.get()).toExternalForm(),
                                    imageButton.getPrefWidth(), imageButton.getPrefHeight(), true, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT
                        )
                );
                imageButton.setBackground(background);

                model.getCurrentAnimals().get(this.id).addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                                        String newValue) {
                        Background background = new Background(
                                new BackgroundImage(
                                        new Image(
                                        getClass().getResource(imgURL.get()).toExternalForm(),
                                            imageButton.getPrefWidth(), imageButton.getPrefHeight(), true, true),
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundPosition.CENTER,
                                        BackgroundSize.DEFAULT
                                )
                        );
                        imageButton.setBackground(background);
                    }
                });
            } catch (NullPointerException e) {
                JSONLogger.err("Invalid image url.", new Pair<>("URL", imgURL.get()));
            }
        }

        imageButton.setOnAction((event -> {
            System.out.println("Hello world!");
        }));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
