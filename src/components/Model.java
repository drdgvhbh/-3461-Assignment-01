package components;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
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
    private JsonNode imageCombinationsNode;
    private int iterations;

    public Model() {
        currentAnimals = new HashMap<>();
        JsonParser jsonParser = null;
        URL path = getClass().getResource("../resources/ImageCombinations.json");
        try {
            jsonParser = new JsonFactory().createParser(path);
            jsonParser.setCodec(new ObjectMapper());
            imageCombinationsNode = jsonParser.readValueAsTree();
        } catch (NullPointerException e) {
            JSONLogger.err("File Not Found.", new Pair<>("File", path.toExternalForm()));
        } catch (IOException e) {
            JSONLogger.err("Parsing Error.", new Pair<>("File", path.toExternalForm()));
        }

        generateImageCombination(iterations);


    }

    public void increaseIterations() {
/*        iterations++;*/
        generateImageCombination(iterations);
    }

    private void buildAnimalTree(String url) {
        URL path = getClass().getResource(url);;
        try {
            animals = new AnimalTree();

            JsonParser jsonParser = new JsonFactory().createParser(path);
            jsonParser.setCodec(new ObjectMapper());
            JsonNode jsonNode = jsonParser.readValueAsTree();

            Queue<JsonNode> animalQueue = new LinkedList<>();
            animalQueue.add(jsonNode);

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
        } catch (NullPointerException e) {
            JSONLogger.err("File Not Found.", new Pair<>("File", path.toExternalForm()));
        } catch (IOException e) {
            JSONLogger.err("Parsing Error.", new Pair<>("File", path.toExternalForm()));
        }


    }

    private void generateImageCombination(int iteration) {
        buildAnimalTree("../resources/AnimalTree.json");
        int i = 0;
        String currentQuery = "";
        try {
            for (ImageBoxId id : ImageBoxId.values()) {
                currentQuery = imageCombinationsNode.get(
                        String.valueOf(iteration)).get(iteration).toString().replace("\"", "");
                if (currentAnimals.get(id) == null) {
                    currentAnimals.put(id, new SimpleStringProperty(animals.getAnimalImages(currentQuery).get(0)));
                } else {
                    currentAnimals.get(id).set(animals.getAnimalImages(currentQuery).get(0));
                }
                String response = animals.getLastQueriedAnimal();
                System.out.println(animals.getLastQueriedAnimal());
                animals.removeAnimal(response);
                i++;
            }
        } catch (NullPointerException e) {
            JSONLogger.warn("Querying animal images that don't exist.", new Pair<>("Query", currentQuery));
        }
    }

    public Map<ImageBoxId, StringProperty> getCurrentAnimals() {
        try {
            return Collections.unmodifiableMap(currentAnimals);
        } catch (NullPointerException e) {
            JSONLogger.err("Animal combinations not initialized.");
        }
        return null;
    }

}
