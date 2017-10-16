package components.StartButton;

import components.AbstractController;
import components.Model;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.util.Pair;
import util.JSONLogger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Represents a start button controller.
 */
public class StartButtonController extends AbstractController {
    /**
     * The button used to start the application.
     */
    @FXML
    private Button startButton;

    /**
     * {@inheritDoc}
     *
     * <p>Causes the application to enter {@link Model.State#PHASE_1} when the <code>start button</code> is pressed.</p>
     */
    @Override
    public void initModel(Model model) throws IllegalStateException {
        super.initModel(model);

        startButton.setOnAction((event -> {
            model.setState(Model.State.PHASE_1);
            JSONLogger.info("User pressed button", new Pair<>("Button", "Start Button"));
        }));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
