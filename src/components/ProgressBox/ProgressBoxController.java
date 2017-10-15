package components.ProgressBox;

import components.AbstractController;
import components.Model;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgressBoxController extends AbstractController {
    /**
     * Represent a box showing whether the user has completed an <code>iteration</code>
     */
    @FXML
    Rectangle progressBox;

    @Override
    public void initModel(Model model) throws IllegalStateException {
        initModel(model, null);

    }

    /**
     * Initializes the controller's dependencies with this application's {@code Model}.
     *
     * <p>Links the <code>progress box</code> to the state of the state of this <code>Model</code>.</p>
     *
     * @param model The application state.
     * @param promptBoxId The identifier for this controller
     * @throws IllegalStateException The model is already initialized.
     */
    public void initModel(Model model, Model.ProgressBoxId promptBoxId) throws IllegalStateException {
        super.initModel(model);

        model.getActiveProgressBoxes().get(promptBoxId).addListener((observable, oldValue, newValue) -> {
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
