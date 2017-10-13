package components.Data;

import java.util.*;

public class SessionData {
    public static float TOTAL_ANSWERS = 20.0f;
    public static float TOTAL_S1_ANSWERS = 10.0f;
    public static float TOTAL_S2_ANSWERS = 10.0f;
    private Date date;
    private int totalCorrectAnswers;
    private float totalCorrectAnswersPercentage;
    private int correctPhaseOneAnswers;
    private float correctPhaseOneAnswersPercentage;
    private int correctPhaseTwoAnswers;
    private float correctPhaseTwoAnswersPercentage;

    private Map<String, IterationData> phaseOneResults;
    private Map<String, IterationData> phaseTwoResults;

    public SessionData() {
        phaseOneResults = new HashMap<>();
        phaseTwoResults = new HashMap<>();
    }

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

    public float getTotalCorrectAnswersPercentage() {
        return totalCorrectAnswersPercentage;
    }

    public void setTotalCorrectAnswersPercentage(float totalCorrectAnswersPercentage) {
        this.totalCorrectAnswersPercentage = totalCorrectAnswersPercentage;
    }

    public int getTotalCorrectAnswers() {
        return totalCorrectAnswers;
    }

    public void setTotalCorrectAnswers(int totalCorrectAnswers) {
        this.totalCorrectAnswers = totalCorrectAnswers;
    }

    public int getCorrectPhaseOneAnswers() {
        return correctPhaseOneAnswers;
    }

    public void setCorrectPhaseOneAnswers(int correctPhaseOneAnswers) {
        this.correctPhaseOneAnswers = correctPhaseOneAnswers;
    }

    public float getCorrectPhaseOneAnswersPercentage() {
        return correctPhaseOneAnswersPercentage;
    }

    public void setCorrectPhaseOneAnswersPercentage(float correctPhaseOneAnswersPercentage) {
        this.correctPhaseOneAnswersPercentage = correctPhaseOneAnswersPercentage;
    }

    public int getCorrectPhaseTwoAnswers() {
        return correctPhaseTwoAnswers;
    }

    public void setCorrectPhaseTwoAnswers(int correctPhaseTwoAnswers) {
        this.correctPhaseTwoAnswers = correctPhaseTwoAnswers;
    }

    public float getCorrectPhaseTwoAnswersPercentage() {
        return correctPhaseTwoAnswersPercentage;
    }

    public void setCorrectPhaseTwoAnswersPercentage(float correctPhaseTwoAnswersPercentage) {
        this.correctPhaseTwoAnswersPercentage = correctPhaseTwoAnswersPercentage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
