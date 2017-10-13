package components.PromptBox;

import components.AbstractController;
import components.Model;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class PromptBoxController extends AbstractController {
    @FXML
    ImageView promptImage;

    @FXML
    Text promptText;

    @Override
    public void initModel(Model model) throws IllegalStateException {
        super.initModel(model);

        model.correctAnimalNameProperty().addListener((observable, oldValue, newValue) -> {
            promptText.setText(newValue);
        });

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
