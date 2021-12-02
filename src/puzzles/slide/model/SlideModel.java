package puzzles.slide.model;

import puzzles.common.Observer;
import puzzles.common.solver.Solver;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SlideModel {
    /** the collection of observers of this model */
    private final List<Observer<SlideModel, SlideClientData>> observers = new LinkedList<>();

    /** the current configuration */
    private SlideConfig currentConfig;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<SlideModel, SlideClientData> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(SlideClientData data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    public SlideModel(String filename) throws IOException {
    }
}
