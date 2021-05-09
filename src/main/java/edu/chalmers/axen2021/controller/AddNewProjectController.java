package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.projectdata.Project;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.model.managers.SaveManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller class for the applications addNewProjectView.fxml.
 * Handles all event triggered in the addNewProjectView.
 * @author Erik Wetter
 * @author Sam Salek
 */
@FXMLController
public class AddNewProjectController {

    /**
     * The parent controller
     */
    private RootController rootController = RootController.getInstance();

    /**
     * The input text field.
     */
    @FXML private TextField projectNameTextField;

    /**
     * Method for closing the addNewProjectView.
     * Puts the addNewProjectView to back in scene.
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

        // Project name input can't be empty.
        if(projectNameTextField.getText().equals("") || projectNameTextField.getText().equals(null)) {
            return;
        }

        // If project name input has illegal characters then don't continue.
        if(!projectNameTextField.getText().matches("^[0-9a-zA-Z\\^\\&\\'\\@\\{\\}\\[\\]\\,\\$\\=\\!\\-\\#\\(\\)\\%\\+\\~\\_ ]+$")) {
            return;
        }

        // Name cant be same as existing project
        for (Project project : ProjectManager.getInstance().getProjects()) {
            if (project.getName().toLowerCase().equals(projectNameTextField.getText().toLowerCase())) {
                return;
            }
        }

        Project newProject = createNewProject();
        ProjectManager.getInstance().setActiveProject(newProject.getName());
        rootController.updateInputView();
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
        SaveManager.getInstance().saveProjectManager();

        return newProject;
    }
}
