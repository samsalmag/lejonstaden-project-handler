package edu.chalmers.axen2021.observers;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for classes that can be observed by an IInputObserver.
 * @author Sam Salek
 * @author Malte Åkvist
 */
public interface IInputObservable {

    /**
     * List of IInputObservers.
     */
    List<IInputObserver> IInputObservers = new ArrayList<>();

    /**
     * Notifies value observers.
     */
    void notifyInputValueObservers();

    /**
     * Notifies comment observers.
     */
    void notifyInputCommentObservers();

    /**
     * Adds an IInputObserver to the observer list.
     */
    void addObserver(IInputObserver iInputObserver);
}
