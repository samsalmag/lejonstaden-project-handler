package edu.chalmers.axen2021.observers;

/**
 * Interface for classes that can be an observer to an IInputObservable.
 * @author Sam Salek
 * @author Malte Åkvist
 */
public interface IInputObserver {

    /**
     * Is called when its observer is notified.
     * Updates by using a inputs variable name and its value.
     */
    void updateValue(String variableName, Double value);

    /**
     * Is called when its observer is notified.
     * Updates by using a inputs variable name and its comment.
     */
    void updateComment(String variableName, String comment);
}
