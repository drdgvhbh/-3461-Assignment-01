package components.Data;

import components.Model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IterationData {
    private String phase;
    private int currentIteration;
    private List<String> animalCombinationQuery;
    private List<String> animalCombination;
    private Map<Model.ImageBoxControllerId, String> labels;
    private String correctAnswer;
    private String chosenAnswer;
    private String textPrompt;
    private boolean isCorrect;

    public IterationData() {
        labels = new TreeMap<>();
    }

    public String getChosenAnswer() {
        return chosenAnswer;
    }

    public void setChosenAnswer(String chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getAnimalCombination() {
        return animalCombination;
    }

    public void setAnimalCombination(List<String> animalCombination) {
        this.animalCombination = animalCombination;
    }

    public List<String> getAnimalCombinationQuery() {
        return animalCombinationQuery;
    }

    public void setAnimalCombinationQuery(List<String> animalCombinationQuery) {
        this.animalCombinationQuery = animalCombinationQuery;
    }

    public int getCurrentIteration() {
        return currentIteration;
    }

    public void setCurrentIteration(int currentIteration) {
        this.currentIteration = currentIteration;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getTextPrompt() {
        return textPrompt;
    }

    public void setTextPrompt(String textPrompt) {
        this.textPrompt = textPrompt;
    }

    public Map<Model.ImageBoxControllerId, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<Model.ImageBoxControllerId, String> labels) {
        this.labels = labels;
    }
}
