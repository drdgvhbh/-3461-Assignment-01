package components;

import components.Data.IterationData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;

public class Iteration {
    public static void run(Model model) {
        if (model.getState() != Model.State.PHASE_1 && model.getState() != Model.State.PHASE_2) {
            return;
        }
        Model.State oldState = model.getState();
        model.setState(Model.State.PAUSED);
        model.resetAnimalImages();
        model.resetAnimalNames();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Model.TIME_BETWEEN_ITERATIONS), actionEvent -> {
            if (model.getState() != Model.State.PAUSED) {
                return;
            }
            model.setState(oldState);
            model.setCurrentIterationData(new IterationData());
            model.getCurrentIterationData().setCurrentIteration(model.getIteration());
            model.getCurrentIterationData().setPhase(model.getState().toString());

            model.generateImageCombination(String.valueOf(model.getIteration() % 10));
            model.buildAnimalTree();
            Model.ImageBoxControllerId[] enumValues = Model.ImageBoxControllerId.values();
            Model.ImageBoxControllerId randomBox = enumValues[model.getRandom().nextInt(enumValues.length)];
            model.getCurrentIterationData().setCorrectAnswer(model.getCurrentAnimalNames().get(randomBox).get());
            model.setCorrectAnimalName(model.getAnimalTree().getGenericName(model.getCurrentAnimalNames().get(randomBox).get(),
                new ArrayList<>(model.getCurrentAnimalNames().values())));
            model.getCurrentIterationData().setTextPrompt(model.getCorrectAnimalName());
            model.setSimilarImageURL(model.getAnimalTree().getAnimalImages(model.getCorrectAnimalName()).get(0).toExternalForm());


            model.getActiveProgressBoxes().get(Model.ProgressBoxId.values()[(model.getIteration() % 10)]).setValue(true);

        }));
        timeline.play();

        model.setTimer(new Timeline(new KeyFrame(Duration.millis(
                Model.TIME_BETWEEN_ITERATIONS + Model.ITERATION_TIME_LIMIT), actionEvent -> {
            if (model.getState() == Model.State.MENU) {
                return;
            }

            model.getCurrentIterationData().setChosenAnswer("DNA");
            model.getCurrentIterationData().setCorrect(false);
            model.getSessionData().addIteration(model.getCurrentIterationData());
            model.increaseIteration();
            Iteration.run(model);
        })));
        model.getTimer().play();
    }
}
