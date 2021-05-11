package edu.chalmers.axen2021.controller.main;

import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.controller.items.ItemType;
import edu.chalmers.axen2021.controller.items.SideBarItemController;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.model.projectdata.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import java.io.IOException;

/**
 * Controller class for the applications sideBar.fxml.
 * Handles all event triggered in the sideBar.
 * @author Oscar Arvidson
 * @author Erik Wetter
 * @author Sam Salek
 */
@FXMLController
public class SideBarController {

    /**
     * The parent controller
     */
    private RootController rootController = RootController.getInstance();

    /**
     * VBox in the sideBar.
     */
    @FXML private VBox projectItemVbox;

    private ToggleGroup projectButtonGroup = new ToggleGroup();

    /**
     * Opens the addNewProject view.
     */
    @FXML
    private void openAddNewProjectView() {
        rootController.openAddNewProjectView();
    }

    /**
     * Adds a new SideBarItem (new Project) to the SideBar with the given name.
     * @param projectName Name of project
     */
    public void addNewSideBarItem(String projectName) {
        FXMLLoader sideBarItem = new FXMLLoader(getClass().getResource("/fxml/sideBarItem.fxml"));
        SideBarItemController sideBarItemController = new SideBarItemController(projectName, projectButtonGroup);
        sideBarItem.setController(sideBarItemController);
        Node sideBarItemNode = null;

        try {
            sideBarItemNode = sideBarItem.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        projectItemVbox.getChildren().add(sideBarItemNode);
    }

    /**
     * Clears all project buttons.
     */
    public void clearAllProjectButtons(){
        projectItemVbox.getChildren().clear();
    }

    /**
     * Initializes all loaded projects by creating a new view for it and adding it to the sidebar.
     */
    public void populateProjectButtons() {
        for (Project project : ProjectManager.getInstance().getProjects()) {
            addNewSideBarItem(project.getName());
        }
    }

    /**
     * Sets the active project button based on project name.
     * @param projectName Name of the project.
     */
    public void setActiveButton(String projectName){
        for(Node button : projectItemVbox.getChildren()){
            if( ( (ToggleButton) button).getText().equals(projectName)){
                projectButtonGroup.selectToggle( (ToggleButton) button);
            }
        }
    }
}
