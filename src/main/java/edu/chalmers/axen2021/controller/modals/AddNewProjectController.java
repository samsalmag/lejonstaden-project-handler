package edu.chalmers.axen2021.controller.modals;

import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.model.projectdata.Project;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.utils.StringUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications addNewProjectView.fxml.
 * Handles all event triggered in the addNewProjectView.
 * @author Erik Wetter
 * @author Sam Salek
 */
@FXMLController
public class AddNewProjectController implements Initializable {

    /**
     * The parent controller
     */
    private final RootController rootController = RootController.getInstance();

    /**
     * Main root node for this .fxml.
     */
    @FXML private VBox mainVBox;

    /**
     * The input text field.
     */
    @FXML private TextField projectNameTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initMainVBox();
        initTextField();
    }

    /**
     * Method for closing the addNewProjectView.
     * Puts the addNewProjectView to back in scene.
     */
    @FXML
    private void closeAddNewProjectView() {
        rootController.closeAddNewProjectView();
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
        if(!projectNameTextField.getText().matches(StringUtils.projectNameRegex)) {
            return;
        }

        // Name cant be same as existing project
        for (Project project : ProjectManager.getInstance().getProjects()) {
            if (project.getName().toLowerCase().equals(projectNameTextField.getText().toLowerCase())) {
                return;
            }
        }

        rootController.addProject(projectNameTextField.getText());
        rootController.updateInputView();
        rootController.closeAddNewProjectView();
    }

    /**
     * Initializes the main root node (the VBox)
     */
    private void initMainVBox() {
        mainVBox.setOnKeyPressed(keyEvent -> {
            // If 'Escape' is pressed then close view
            if(keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                closeAddNewProjectView();
            }
        });
    }

    /**
     * Initializes the TextField for project name input.
     */
    private void initTextField() {
        projectNameTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {

            // If no input is made / if removed last character
            if(newValue.equals("") || newValue == null){
                projectNameTextField.setText("");
            }

            // Check if it matches the regex
            if(!newValue.matches(StringUtils.projectNameRegex)){
                projectNameTextField.setText(oldValue);
            }
        });

        // On key pressed event
        projectNameTextField.setOnKeyPressed(keyEvent -> {
            // If 'Enter' is pressed then add new project.
            if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                addNewProject();
            }
        });
    }

    /**
     * Getter for the TextField for project name input.
     * @return
     */
    public TextField getProjectNameTextField() {
        return projectNameTextField;
    }
}
