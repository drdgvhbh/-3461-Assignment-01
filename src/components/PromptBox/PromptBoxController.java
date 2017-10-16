package components.PromptBox;

import components.AbstractController;
import components.Model;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Represents a prompt box controller.
 */
public class PromptBoxController extends AbstractController {
    /**
     * The image that represents the <code>prompt text</code>.
     */
    @FXML
    ImageView promptImage;

    /**
     * The text message that represents the image the user needs to match.
     */
    @FXML
    Text promptText;

    /**
     * {@inheritDoc}
     *
     *  <p>Links the <code>prompt image</code> and <code>prompt text </code> to the state of this <code>Model</code>.</p>
     */
    @Override
    public void initModel(Model model) throws IllegalStateException {
        super.initModel(model);

        // Change the prompt text when the correct name is updated
        model.correctAnimalNameProperty().addListener((observable, oldValue, newValue) -> {
            promptText.setText(newValue);
        });

        // Change the prompt image when the animal images are updated
        model.similarImageURLProperty().addListener((observable, oldValue, newValue) -> {
            promptImage.setImage(new Image(
                newValue,
                promptImage.getFitWidth(), promptImage.getFitHeight(),
                true,
                true
            ));
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
