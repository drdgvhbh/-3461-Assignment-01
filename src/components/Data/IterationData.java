package components.Data;

import components.Model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the raw data for a single iteration. This purpose of this class is to be convert to a JSON file using
 * GSON.
 */
public class IterationData {
    /**
     * The phase of this iteration
     */
    private String phase;

    /**
     * The current iteration number.
     */
    private int currentIteration;

    /**
     * The animal combination requested.
     */
    private List<String> animalCombinationQuery;

    /**
     * The animal combination provided.
     *
     */
    private List<String> animalCombination;

    /**
     * The labels over the image buttons.
     */
    private Map<Model.ImageBoxControllerId, String> labels;

    /**
     * The name of the animal the user needs to match an image to.
     */
    private String correctAnswer;

    /**
     * The name of the animal they user chose.
     */
    private String chosenAnswer;

    /**
     * The generic name that was shown to the user.
     */
    private String textPrompt;

    /**
     * Indicates whether the user chose the correct answers.
     */
    private boolean isCorrect;


    /**
     * Initializes a new instance of the {@link IterationData} class.
     */
    public IterationData() {
        labels = new TreeMap<>();
    }

    /**
     * Gets the answer chosen by the user.
     * @return the answer chosen by the user.
     */
    public String getChosenAnswer() {
        return chosenAnswer;
    }

    /**
     * Sets the answer chosen by the user.
     * @param chosenAnswer the chosen answer
     */
    public void setChosenAnswer(String chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    /**
     * Gets the correct answer.
     * @return the correct answer.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Sets the correct answer.
     * @param correctAnswer the correct answer
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Gets the collection of images displayed to the user.
     * @return the collection of images displayed to the user.
     */
    public List<String> getAnimalCombination() {
        return animalCombination;
    }

    /**
     * Sets the collection of images displayed to the user.
     * @param animalCombination the collection of images displayed to the user
     */
    public void setAnimalCombination(List<String> animalCombination) {
        this.animalCombination = animalCombination;
    }

    /**
     * Gets the names of the animals requested.
     * @return the names of the animals requested
     */
    public List<String> getAnimalCombinationQuery() {
        return animalCombinationQuery;
    }

    /**
     * Sets the names of the animals requested.
     * @param animalCombinationQuery the names of the animals requested
     */
    public void setAnimalCombinationQuery(List<String> animalCombinationQuery) {
        this.animalCombinationQuery = animalCombinationQuery;
    }

    /**
     * Gets the current iteration number.
     * @return the current iteration number.
     */
    public int getCurrentIteration() {
        return currentIteration;
    }

    /**
     * Sets the current iteration number.
     * @param currentIteration the current iteration number.
     */
    public void setCurrentIteration(int currentIteration) {
        this.currentIteration = currentIteration;
    }

    /**
     * Gets the current phase of the application.
     * @return the current phase of the application
     */
    public String getPhase() {
        return phase;
    }

    /**
     * Sets the current phase of the application.
     * @param phase the current phase of the application
     */
    public void setPhase(String phase) {
        this.phase = phase;
    }

    /**
     * Gets a value indicating whether the user's answer was correct.
     * @return <code>true</code> if the user's answer was correct; otherwise, <code>false</code>.
     */
    public boolean isCorrect() {
        return isCorrect;
    }

    /**
     * Sets whether the user's answer was correct.
     * @param correct a value indicating whether the answer was correct.
     */
    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }


    /**
     * Gets the text prompt that is being displayed to the user.
     * @return the text prompt that is being displayed to the user
     */
    public String getTextPrompt() {
        return textPrompt;
    }

    /**
     * Sets the text prompt.
     * @param textPrompt the text prompt
     */
    public void setTextPrompt(String textPrompt) {
        this.textPrompt = textPrompt;
    }

    /**
     * Gets the labels.
     * @return the labels
     */
    public Map<Model.ImageBoxControllerId, String> getLabels() {
        return labels;
    }

    /**
     * Sets the labels.
     * @param labels the labels
     */
    public void setLabels(Map<Model.ImageBoxControllerId, String> labels) {
        this.labels = labels;
    }
}
