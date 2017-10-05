package components.ImageBox;

import components.AbstractController;
import components.AnimalTree.AnimalNode;
import components.Model;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
        super.initModel(model);

        imageButton.setOnAction((event -> {
            System.out.println("Hello world!");
        }));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(imageButton.getId());
    }

    public void setId(Model.ImageBoxId id) {
        this.id = id;
    }

}
