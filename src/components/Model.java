package components;

import components.AnimalTree.AnimalTree;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.beans.PropertyChangeListener;
import java.util.*;

public class Model {
    public enum ImageBoxId {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT

    };
    private AnimalTree animals;
    private Map<ImageBoxId, StringProperty> currentAnimals;

    public Model() {
        // Setup the connection between all our animal images
        animals = new AnimalTree();
        buildAnimalTree();

        currentAnimals = new HashMap<>() {{
            put(ImageBoxId.TOP_LEFT, new SimpleStringProperty(animals.getAnimalImages("Bears").get(0)));
        }};

        System.out.println(animals.getAnimalImages("Bears").get(0));

    }

    private void buildAnimalTree() {
        // First level
        animals.addRoot("Animals", null);

        // Second level
        animals.addAnimal("Mammals", null, "Animals");

        // Third Level
        animals.addAnimal("Bears", null,"Mammals");

        //Fourth level
        animals.addAnimal("BrownBear","@../../resources/brown_bear.jpg", "Bears");
    }

    public Map<ImageBoxId, StringProperty> getCurrentAnimals() {
        return Collections.unmodifiableMap(currentAnimals);
    }

}
