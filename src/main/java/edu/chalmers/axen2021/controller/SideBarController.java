package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.SaveManager;
import edu.chalmers.axen2021.observers.IInputObservable;
import edu.chalmers.axen2021.observers.IInputObserver;
import edu.chalmers.axen2021.observers.IViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications sideBar.fxml.
 * Handles all event triggered in the sideBar.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
public class SideBarController implements Initializable, IInputObservable {

    private SaveManager saveManager = SaveManager.getInstance();

    /**
     * TilePane in the sideBar.
     */
    @FXML private TilePane projectTilePane;

    /**
     * Method for on addProject button clicked.
     * Adds sideBarItem to the tilePane for the new project.
     * @param event of action.
     */
    @FXML
    private void addNewProject(ActionEvent event) throws IOException {
        //ToDo add functionality: Fill in input for new project and show it in the centerStage view.
        Node sideBarItem = FXMLLoader.load(getClass().getResource("/fxml/sideBarItem.fxml"));
        projectTilePane.getChildren().add(sideBarItem);

        // TODO - Improve implementation after fxml is done.
        notifyInputValueObservers();
        saveManager.saveProject();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addObserver(saveManager);
    }

    @Override
    public void notifyInputValueObservers() {
        for (IInputObserver inputObserver : IInputObservers) {
            inputObserver.updateValue("test1", 342.0);
        }
    }

    @Override
    public void notifyInputCommentObservers() {
        // No comment input fields exists yet.
    }

    @Override
    public void addObserver(IInputObserver iInputObserver) {
        IInputObservers.add(iInputObserver);
    }
}
