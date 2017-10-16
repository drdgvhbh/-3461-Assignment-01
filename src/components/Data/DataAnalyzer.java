package components.Data;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import util.JSONLogger;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a tool for analyzing the raw data produced by this application. The purpose of this class is to be
 * converted into a JSON file using GSON.
 */
public class DataAnalyzer {
    /**
     * Represents the number of correct iterations for each iteration in Phase 1.
     */
    private Map<String, Integer> phaseOneCorrectByIteration;

    /**
     * Represents the number of correct iterations for each iteration in Phase 2.
     */
    private Map<String, Integer> phaseTwoCorrectByIteration;

    /**
     * The total sample size.
     */
    private int totalSampleSize;

    /**
     * The total number of iterations.
     */
    private int totalIterations;

    /**
     * Number of times an animal was prompted to the user in Phase 1.
     */
    private Map<String, Integer> numberOfTimesAnimalPromptedP1;

    /**
     * Number of times an animal was correctly matched by to the user in Phase 1.
     */
    private Map<String, Integer> numberOfTimesAnimalCorrectlyChosenP1;

    /**
     * Number of times an animal was prompted to the user in Phase 2.
     */
    private Map<String, Integer> numberOfTimesAnimalPromptedP2;

    /**
     * Number of times an animal was correctly matched by to the user in Phase 2.
     */
    private Map<String, Integer> numberOfTimesAnimalCorrectlyChosenP2;

    /**
     * Total number of correct answers in Phase 1.
     */
    private int totalCorrectPhaseOneAnswers;

    /**
     * Total number of correct answers in Phase 2.
     */
    private int totalCorrectPhaseTwoAnswers;


    /**
     * Initializes a new instance of the {@link DataAnalyzer} class.
     */
    public DataAnalyzer() {
        phaseOneCorrectByIteration = new HashMap<>();
        phaseTwoCorrectByIteration = new HashMap<>();
        numberOfTimesAnimalPromptedP1 = new TreeMap<>();
        numberOfTimesAnimalCorrectlyChosenP1 = new TreeMap<>();
        numberOfTimesAnimalPromptedP2 = new TreeMap<>();
        numberOfTimesAnimalCorrectlyChosenP2 = new TreeMap<>();
        totalSampleSize = 0;
        totalIterations = 0;
        totalCorrectPhaseOneAnswers = 0;
        totalCorrectPhaseTwoAnswers = 0;

        // Read the files
        URI path = null;
        String pathString = "/resources/results";
        try {
            path = getClass().getResource(pathString).toURI();
        } catch (Exception e) {
            JSONLogger.warn("Path does not exist.", new Pair<>("Path", pathString));
        }
        if (path == null) {
            JSONLogger.warn("Path does not exist.", new Pair<>("Path", pathString));
            return;
        }
        File[] listOfFiles = new File(path).listFiles();
        if (listOfFiles == null) {
            JSONLogger.warn("Path is not a directory or is empty.", new Pair<>("Path", pathString));
            return;
        }
        for (File file : listOfFiles) {
            if (file == null || !file.isFile()) {
                return;
            }
            JsonParser jsonParser = null;
            JsonNode jsonNode = null;
            try {
                jsonParser = new JsonFactory().createParser(file.toURI().toURL());
                jsonParser.setCodec(new ObjectMapper());
                jsonNode = jsonParser.readValueAsTree();

                // Add correct answers
                totalCorrectPhaseOneAnswers += jsonNode.get("correctPhaseOneAnswers").asInt();
                totalCorrectPhaseTwoAnswers += jsonNode.get("correctPhaseTwoAnswers").asInt();

                // Get correct answers from phase 1
                Iterator<Map.Entry<String, JsonNode>> it = jsonNode.get("phaseOneResults").fields();
                while (it.hasNext()) {
                    totalIterations++;
                    Map.Entry<String, JsonNode> entry = it.next();
                    if (entry.getValue().get("isCorrect").asBoolean()) {
                        if (!phaseOneCorrectByIteration.containsKey(entry.getKey())) {
                            phaseOneCorrectByIteration.put(entry.getKey(), 1);
                        } else {
                            phaseOneCorrectByIteration.put(entry.getKey(),
                                phaseOneCorrectByIteration.get(entry.getKey()) + 1);
                        }
                        if (!numberOfTimesAnimalCorrectlyChosenP1.containsKey(entry.getValue().get("textPrompt").asText())) {
                            numberOfTimesAnimalCorrectlyChosenP1.put(entry.getValue().get("textPrompt").asText(), 1);
                        } else {
                            numberOfTimesAnimalCorrectlyChosenP1.put(entry.getValue().get("textPrompt").asText(),
                                numberOfTimesAnimalCorrectlyChosenP1.get(entry.getValue().get("textPrompt").asText()) + 1);
                        }
                    } else {
                        if (!numberOfTimesAnimalCorrectlyChosenP1.containsKey(entry.getValue().get("textPrompt").asText())) {
                            numberOfTimesAnimalCorrectlyChosenP1.put(entry.getValue().get("textPrompt").asText(), 0);
                        }
                    }

                    if (!numberOfTimesAnimalPromptedP1.containsKey(entry.getValue().get("textPrompt").asText())) {
                        numberOfTimesAnimalPromptedP1.put(entry.getValue().get("textPrompt").asText(), 1);
                    } else {
                        numberOfTimesAnimalPromptedP1.put(entry.getValue().get("textPrompt").asText(),
                            numberOfTimesAnimalPromptedP1.get(entry.getValue().get("textPrompt").asText()) + 1);
                    }
                }

                // Get correct answers from phase 2
                it = jsonNode.get("phaseTwoResults").fields();
                while (it.hasNext()) {
                    totalIterations++;
                    Map.Entry<String, JsonNode> entry = it.next();
                    if (entry.getValue().get("isCorrect").asBoolean()) {
                        if (!phaseTwoCorrectByIteration.containsKey(entry.getKey())) {
                            phaseTwoCorrectByIteration.put(entry.getKey(), 1);
                        } else {
                            phaseTwoCorrectByIteration.put(entry.getKey(),
                                phaseTwoCorrectByIteration.get(entry.getKey()) + 1);
                        }
                        if (!numberOfTimesAnimalCorrectlyChosenP2.containsKey(entry.getValue().get("textPrompt").asText())) {
                            numberOfTimesAnimalCorrectlyChosenP2.put(entry.getValue().get("textPrompt").asText(), 1);
                        } else {
                            numberOfTimesAnimalCorrectlyChosenP2.put(entry.getValue().get("textPrompt").asText(),
                                numberOfTimesAnimalCorrectlyChosenP2.get(entry.getValue().get("textPrompt").asText()) + 1);
                        }
                    } else {
                        if (!numberOfTimesAnimalCorrectlyChosenP2.containsKey(entry.getValue().get("textPrompt").asText())) {
                            numberOfTimesAnimalCorrectlyChosenP2.put(entry.getValue().get("textPrompt").asText(), 0);
                        }
                    }

                    if (!numberOfTimesAnimalPromptedP2.containsKey(entry.getValue().get("textPrompt").asText())) {
                        numberOfTimesAnimalPromptedP2.put(entry.getValue().get("textPrompt").asText(), 1);
                    } else {
                        numberOfTimesAnimalPromptedP2.put(entry.getValue().get("textPrompt").asText(),
                            numberOfTimesAnimalPromptedP2.get(entry.getValue().get("textPrompt").asText()) + 1);
                    }
                }
                totalSampleSize++;

            } catch (IOException e) {
                JSONLogger.warn("File is not a valid JSON.", new Pair<>("File", file.getName()));
                return;
            }

        }

    }
}
