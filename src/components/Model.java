package components;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import components.AnimalTree.AnimalTree;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;
import jdk.nashorn.api.scripting.URLReader;
import util.JSONLogger;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
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
        buildAnimalTree("../resources/AnimalTree.json");

        String currentQuery = "";
        try {
            currentAnimals = new HashMap<>();
            currentQuery = "Bears";
            currentAnimals.put(ImageBoxId.TOP_LEFT,
                    new SimpleStringProperty(animals.getAnimalImages(currentQuery).get(0)));
            currentQuery = "Giraffes";
            currentAnimals.put(ImageBoxId.TOP_RIGHT,
                    new SimpleStringProperty(animals.getAnimalImages(currentQuery).get(0)));
            currentQuery = "Pandas";
            currentAnimals.put(ImageBoxId.BOTTOM_LEFT,
                    new SimpleStringProperty(animals.getAnimalImages(currentQuery).get(0)));
            currentQuery = "Rabbits";
            currentAnimals.put(ImageBoxId.BOTTOM_RIGHT,
                    new SimpleStringProperty(animals.getAnimalImages(currentQuery).get(0)));
        } catch (NullPointerException e) {
            JSONLogger.err("Querying Animal images that don't exist.", new Pair<>("Query", currentQuery));
            currentAnimals = new HashMap<>();
        }

    }

    private void buildAnimalTree(String url) {
        URL path = null;
        try {
            path = getClass().getResource(url);

            JsonParser jsonParser = new JsonFactory().createParser(path);
            jsonParser.setCodec(new ObjectMapper());
            JsonNode jsonNode = jsonParser.readValueAsTree();

            Queue<JsonNode> animalQueue = new LinkedList<>() {{
                add(jsonNode);
            }};

            Queue<String> parentQueue = new LinkedList<>() {{
                add(null);
            }};
            while (!animalQueue.isEmpty()) {
                JsonNode node = animalQueue.remove();
                Iterator<Map.Entry<String, JsonNode>> it = node.fields();
                while (it.hasNext()) {
                    Map.Entry<String, JsonNode> entry = it.next();
                    if (entry.getValue().isObject()) {
                        animals.addAnimal(entry.getKey(), null, parentQueue.poll());
                        for (int i = 0; i < entry.getValue().size(); i++) {
                            parentQueue.add(entry.getKey());
                        }
                        animalQueue.add(entry.getValue());
                    } else {
                        animals.addAnimal(
                            entry.getKey(),
                            entry.getValue().toString().replace("\"", ""),
                            parentQueue.poll()
                        );
                    }

                }

            }

        } catch (FileNotFoundException e) {
            JSONLogger.err("File Not Found.", new Pair<>("File", path.toExternalForm()));
        } catch (IOException e) {
            JSONLogger.err("JSON parsing error.", new Pair<>("File", path.toExternalForm()));
        }
    }

    public Map<ImageBoxId, StringProperty> getCurrentAnimals() {
        return Collections.unmodifiableMap(currentAnimals);
    }

}
