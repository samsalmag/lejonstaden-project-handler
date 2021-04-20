package edu.chalmers.axen2021.observers;

/**
 * Interface used to observe views
 * @author Malte Åkvist
 */
public interface IViewObserver {
    /**
     * Updates by using a views fxml name in order to change view when notified by an observer.
     */
    void update(String fxmlName);
}
