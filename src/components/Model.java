package components;

import components.AnimalTree.AnimalTree;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private AnimalTree animals;
    private List<StringProperty> currentAnimals;

    public Model() {
        // Setup the connection between all our animal images
        animals = new AnimalTree();
        buildAnimalTree();

        currentAnimals = new ArrayList<>() {{
            add(new SimpleStringProperty(animals.getAnimalImages("Bears").get(0)));
        }};

        for (StringProperty imgURL : currentAnimals) {
            imgURL.addListener();
        }

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

}
