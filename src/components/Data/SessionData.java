package components.Data;

import java.util.*;

/**
 * Represents the raw data of the entire session. The purpose of this class is to be converted to a JSON file using
 * GSON.
 */
public class SessionData {
    /**
     * The total number of answers in a single session.
     */
    public static float TOTAL_ANSWERS = 20.0f;

    /**
     * The total number of answers in Phase 1.
     */
    public static float TOTAL_S1_ANSWERS = 10.0f;

    /**
     * The total number of answers in Phase 2.
     */
    public static float TOTAL_S2_ANSWERS = 10.0f;

    /**
     * The Date.
     */
    private Date date;

    /**
     * Total number of correct answers in the entire session.
     */
    private int totalCorrectAnswers;

    /**
     * The decimal percentage of the the total number of correct answers.
     */
    private float totalCorrectAnswersPercentage;

    /**
     * The number of correct answers in Phase 1.
     */
    private int correctPhaseOneAnswers;

    /**
     * The decimal percentage of the number of correct answers in Phase 1.
     */
    private float correctPhaseOneAnswersPercentage;

    /**
     * The number of correct answers in Phase 2.
     */
    private int correctPhaseTwoAnswers;


    /**
     * The decimal percentage of the number of correct answers in Phase 2.
     */
    private float correctPhaseTwoAnswersPercentage;

    /**
     * The results of Phase 1.
     */
    private Map<String, IterationData> phaseOneResults;

    /**
     * The results of Phase 2.
     */
    private Map<String, IterationData> phaseTwoResults;

    /**
     * Initializes a new instance of the {@link SessionData} class.
     */
    public SessionData() {
        phaseOneResults = new HashMap<>();
        phaseTwoResults = new HashMap<>();
    }

    /**
     * Adds an iteration to the session.
     * @param it the iteration.
     */
    public void addIteration(IterationData it) {
        if (it.getPhase().equals("PHASE_1")) {
            phaseOneResults.put(String.valueOf(it.getCurrentIteration()), it);
            if (it.isCorrect()) {
                totalCorrectAnswers += 1;
                correctPhaseOneAnswers += 1;
            }
            return;
        }
        if (it.getPhase().equals("PHASE_2")) {
            phaseTwoResults.put(String.valueOf(it.getCurrentIteration()), it);
            if (it.isCorrect()) {
                totalCorrectAnswers += 1;
                correctPhaseTwoAnswers += 1;
            }
            return;
        }
    }

    /**
     * Gets the decimal percentage of correct answers for the entire session.
     * @return the decimal percentage of correct answers for the entire session
     */
    public float getTotalCorrectAnswersPercentage() {
        return totalCorrectAnswersPercentage;
    }

    /**
     * Sets the decimal percentage of correct answers for the entire session.
     * @param totalCorrectAnswersPercentage the decimal percentage of correct answers for the entire session
     */
    public void setTotalCorrectAnswersPercentage(float totalCorrectAnswersPercentage) {
        this.totalCorrectAnswersPercentage = totalCorrectAnswersPercentage;
    }

    /**
     * Gets the total number of correct answers over the session.
     * @return the total number of correct answers over the session
     */
    public int getTotalCorrectAnswers() {
        return totalCorrectAnswers;
    }

    /**
     * Sets the total number of correct answers over the session.
     * @param totalCorrectAnswers he total number of correct answers over the session
     */
    public void setTotalCorrectAnswers(int totalCorrectAnswers) {
        this.totalCorrectAnswers = totalCorrectAnswers;
    }

    /**
     * Gets the number of correct answers in Phase 1.
     * @return the number of correct answers in Phase 1.
     */
    public int getCorrectPhaseOneAnswers() {
        return correctPhaseOneAnswers;
    }

    /**
     * Sets the number of correct answers in Phase 1.
     * @param correctPhaseOneAnswers  the number of correct answers in Phase 1.
     */
    public void setCorrectPhaseOneAnswers(int correctPhaseOneAnswers) {
        this.correctPhaseOneAnswers = correctPhaseOneAnswers;
    }

    /**
     * Gets the decimal percentage of correct answers for Phase 1.
     * @return the decimal percentage of correct answers for Phase 1
     */
    public float getCorrectPhaseOneAnswersPercentage() {
        return correctPhaseOneAnswersPercentage;
    }

    /**
     * Sets the decimal percentage of correct answers for Phase 1.
     * @param correctPhaseOneAnswersPercentage the decimal percentage of correct answers for Phase 1
     */
    public void setCorrectPhaseOneAnswersPercentage(float correctPhaseOneAnswersPercentage) {
        this.correctPhaseOneAnswersPercentage = correctPhaseOneAnswersPercentage;
    }

    /**
     * Gets the number of correct answers in Phase 2.
     * @return the number of correct answers in Phase 2.
     */
    public int getCorrectPhaseTwoAnswers() {
        return correctPhaseTwoAnswers;
    }

    /**
     * Sets the number of correct answers in Phase 2.
     * @param correctPhaseTwoAnswers  the number of correct answers in Phase 2.
     */
    public void setCorrectPhaseTwoAnswers(int correctPhaseTwoAnswers) {
        this.correctPhaseTwoAnswers = correctPhaseTwoAnswers;
    }

    /**
     * Gets the decimal percentage of correct answers for Phase 2.
     * @return the decimal percentage of correct answers for Phase 2
     */
    public float getCorrectPhaseTwoAnswersPercentage() {
        return correctPhaseTwoAnswersPercentage;
    }

    /**
     * Sets the decimal percentage of correct answers for Phase 2.
     * @param correctPhaseTwoAnswersPercentage the decimal percentage of correct answers for Phase 2
     */
    public void setCorrectPhaseTwoAnswersPercentage(float correctPhaseTwoAnswersPercentage) {
        this.correctPhaseTwoAnswersPercentage = correctPhaseTwoAnswersPercentage;
    }

    /**
     * Gets the date.
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date.
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
