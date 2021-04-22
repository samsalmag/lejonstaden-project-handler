package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.Project;
import edu.chalmers.axen2021.model.SaveManager;
import edu.chalmers.axen2021.observers.IInputObservable;
import edu.chalmers.axen2021.observers.IInputObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller class for the applications sideBar.fxml.
 * Handles all event triggered in the sideBar.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
public class SideBarController implements Initializable, IInputObservable {

    /**
     * VBox in the sideBar.
     */
    @FXML private VBox projectItemVbox;

    /**
     * Method for on addProject button clicked.
     * Adds sideBarItem to the VBox for the new project.
     * @param event of action.
     */
    @FXML
    private void addNewProject(ActionEvent event) throws IOException {
        //ToDo add functionality: Fill in input for new project and show it in the centerStage view.
        Node sideBarItem = FXMLLoader.load(getClass().getResource("/fxml/sideBarItem.fxml"));
        projectItemVbox.getChildren().add(sideBarItem);

        // TODO - Improve implementation after fxml is done.
        //notifyInputValueObservers();

        SaveManager.getInstance().saveProject(new Project(String.valueOf(new Date().getTime())));
        //Project project = SaveManager.getInstance().readProject("1619129916294");

        System.out.println("test");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //addObserver(saveManager);

        ArrayList<Project> projects = SaveManager.getInstance().readProjects();
        for (Project project : projects) {
            Node sideBarItem = null;
            try {
                sideBarItem = FXMLLoader.load(getClass().getResource("/fxml/sideBarItem.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            projectItemVbox.getChildren().add(sideBarItem);
        }
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
