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

    /**
     * Opens the addNewProject view.
     */
    @FXML
    private void openAddNewProjectView() {
        rootController.getAddNewProjectAnchorPane().toFront();
        rootController.focusTextField(ItemType.PROJECT);   // Focus the text field for project name input.
    }

    /**
     * Adds a new SideBarItem (new Project) to the SideBar with the given name.
     * @param projectName Name of project
     */
    public void addNewSideBarItem(String projectName) {
        FXMLLoader sideBarItem = new FXMLLoader(getClass().getResource("/fxml/sideBarItem.fxml"));
        SideBarItemController sideBarItemController = new SideBarItemController(projectName);
        sideBarItem.setController(sideBarItemController);
        Node sideBarItemNode = null;

        try {
            sideBarItemNode = sideBarItem.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        projectItemVbox.getChildren().add(sideBarItemNode);
    }

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
}
