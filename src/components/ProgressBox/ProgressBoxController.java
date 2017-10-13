package components.ProgressBox;

import components.AbstractController;
import components.Model;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgressBoxController extends AbstractController {
    @FXML
    Rectangle progressBox;

    private Model.ProgressBoxId id;

    @Override
    public void initModel(Model model) throws IllegalStateException {
        initModel(model, null);

    }

    public void initModel(Model model, Model.ProgressBoxId promptBoxId) throws IllegalStateException {
        super.initModel(model);
        this.id = promptBoxId;

        model.getActiveProgressBoxes().get(this.id).addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                progressBox.setFill(Color.DEEPSKYBLUE);
            } else {
                progressBox.setFill(Color.WHITE);
            }
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
