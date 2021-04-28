package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.Project;
import edu.chalmers.axen2021.model.ProjectManager;
import edu.chalmers.axen2021.model.SaveManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddNewProjectController {

    private RootController rootController = RootController.getInstance();

    @FXML private TextField projectNameTextField;

    /**
     * Method for closing the modalWindow.
     * Puts the modalWindowAnchorPane to back in scene.
     * @param event that triggered the method.
     */
    @FXML
    private void closeAddNewProjectView(Event event) {
        rootController.getAddNewProjectAnchorPane().toBack();
        projectNameTextField.clear();
    }

    /**
     * Method for on addProject button clicked.
     * Adds sideBarItem to the SideBar for the new project.
     */
    @FXML
    private void addNewProject() {

        // Name cant be same as existing project
        for (Project project : ProjectManager.getInstance().getProjects()) {
            if (project.getName().equals(projectNameTextField.getText())) {
                return;
            }
        }

        Project newProject = createNewProject();
        ProjectManager.getInstance().setActiveProject(newProject.getName());
        rootController.getAddNewProjectAnchorPane().toBack();
        projectNameTextField.clear();
    }

    /**
     * Creates a new project (sidebarItem) and adds it to the SideBar.
     * Also creates a save-file for the project.
     */
    private Project createNewProject() {
        rootController.getSideBarController().addNewSideBarItem(projectNameTextField.getText());
        Project newProject = new Project(projectNameTextField.getText());
        SaveManager.getInstance().saveProject(newProject);

        // TODO - Move this somewhere else..?
        SaveManager.getInstance().saveProjectManager();

        return newProject;
    }
}
