package edu.chalmers.axen2021.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for a ViewObservable which will notify observers
 * when a button is clicked so that the view can be changed
 * @author Malte Åkvist
 */
public interface IViewObservable {

    /**
     * List of IViewObservers.
     */
    List<IViewObserver> IViewObservers = new ArrayList<>();

    /**
     * Notifies observers
     */
    void notifyObservers();

    /**
     * Adds an IViewObserver to the observer list
     */
    void addObserver(IViewObserver iViewObserver);
}
