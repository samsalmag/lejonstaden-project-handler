package edu.chalmers.axen2021.controller.items;

import edu.chalmers.axen2021.model.managers.PdfManager;
import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.model.managers.SaveManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications sideBarItem.fxml.
 * Handles all event triggered in the sideBarItem.
 * @author Oscar Arvidson
 * @author Erik Wetter
 * @author Sam Salek
 * @author Malte Åkvist
 */
@FXMLController
public class SideBarItemController implements Initializable {

    /**
     * Parent Controller.
     */
    private RootController rootController = RootController.getInstance();

    /**
     * Button for opening an existing project.
     */
    @FXML
    private ToggleButton button;

    /**
     * Name of the project bound to the button.
     */
    private String projectName;

    private ToggleGroup projectButtonGroup;

    /**
     * Constructor that initialize the project name.
     * @param projectName name of the project.
     * @param projectButtonGroup
     */
    public SideBarItemController(String projectName, ToggleGroup projectButtonGroup) {
        this.projectName = projectName;
        this.projectButtonGroup = projectButtonGroup;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button.setText(projectName);
        button.setToggleGroup(projectButtonGroup);
    }

    /**
     * Method for creating Pdf when button is clicked
     */
    @FXML
    private void createPdf() {
        rootController.createPdf(projectName);
    }

    /**
     * Method for when button is clicked
     * @param event of action.
     */
    @FXML
    private void onClick(ActionEvent event) {
        //Save the previously active project
        if(ProjectManager.getInstance().getActiveProject() != null) {
            SaveManager.getInstance().saveProject(ProjectManager.getInstance().getActiveProject());
        }

        ProjectManager.getInstance().setActiveProject(button.getText());
        rootController.setActiveProjectButton(projectName);
        rootController.updateSummaryView();
    }

    /**
     * Removes cost item.
     */
    @FXML
    private void openRemoveConfirmation(){
        rootController.openConfirmationView(projectName, ItemType.PROJECT);
    }
}
