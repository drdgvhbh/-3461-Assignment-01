package components;

import javafx.fxml.Initializable;
import javafx.util.Pair;
import util.JSONLogger;

/**
 * Represents an FXML controller.
 */
public abstract class AbstractController implements Initializable {
    /**
     * Represents the state of this application.
     */
    protected Model model;

    /**
     * Initializes the controller's dependencies with this application's <code>Model</code>.
     *
     * @param model The application state.
     * @throws IllegalStateException The model is already initialized.
     */
    public void initModel(Model model) throws IllegalStateException {
        if (this.model != null) {
            JSONLogger.err("Model is already initialized.", new Pair<>("Class", this.getClass().getName()));
            throw new IllegalStateException();
        }
        this.model = model;
    }
}
