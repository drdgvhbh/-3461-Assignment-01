package components.ImageBox;

import components.AbstractController;
import components.AnimalTree.AnimalNode;
import components.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ImageBoxController extends AbstractController {
    private Model model;

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
    }
}
