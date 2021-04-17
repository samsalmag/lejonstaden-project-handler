package edu.chalmers.axen2021.controller;

/**
 * Controller class for the applications SummaryView.fxml.
 * Handles all event triggered in the SummaryView.
 * @author Erik Wetter
 * @author Oscar Arvidson
 */
public class SummaryViewController implements IViewObservable {

    /**
     * Notifies observers of the view that is clicked
     */
    @Override
    public void notifyObservers() {
        for (IViewObserver IViewObserver : IViewObservers) {
            //IViewObserver.update(fxmlName);
        }
    }

    /**
     * Adds an observer to the viewObserver list
     * @param iViewObserver a viewObserver
     */
    @Override
    public void addObserver(IViewObserver iViewObserver) {
        IViewObservers.add(iViewObserver);
    }
}
