package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.Model;
import edu.chalmers.axen2021.model.Project;
import edu.chalmers.axen2021.model.ProjectManager;
import edu.chalmers.axen2021.model.SaveManager;
import edu.chalmers.axen2021.observers.IViewObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Controller singleton class for the applications root.fxml.
 * Initialize starting page and all its nodes.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
public class RootController implements IViewObserver {

    /**
     * Instance of RootController class.
     */
    private static RootController instance = null;

    private HeaderController headerController;
    private SideBarController sideBarController;
    private ModalController modalController;
    private InputController inputController;
    private AddNewProjectController addNewProjectController;

    //Because of singleton pattern.
    private RootController() {}

    /**
     * Getter for instance of this class.
     * @return instance of this class.
     */
    public static RootController getInstance() {
        if(instance == null) {
            instance = new RootController();
        }
        return instance;
    }

    /**
     * Header AnchorPane in root.fxml
     */
    @FXML private AnchorPane headerAnchorPane;
    /**
     * Sidebar AnchorPane in root.fxml
     */
    @FXML private AnchorPane sideBarAnchorPane;
    /**
     * CenterStage AnchorPane in root.fxml
     */
    @FXML private AnchorPane centerStageAnchorPane;
    /**
     * Modal AnchorPane in root.fxml
     */
    @FXML private AnchorPane modalAnchorPane;
    /**
     * AnchorPane for addNewProjectView.fxml in root.fxml
     */
    @FXML private AnchorPane addNewProjectAnchorPane;

    /**
     * Initialize method that starts up the first scene and all its children.
     */
    public void initialize() {

        FXMLLoader header = new FXMLLoader(getClass().getResource("/fxml/header.fxml"));
        FXMLLoader sideBar = new FXMLLoader(getClass().getResource("/fxml/sideBar.fxml"));
        FXMLLoader modalWindow = new FXMLLoader(getClass().getResource("/fxml/modalWindow.fxml"));
        FXMLLoader addNewProject = new FXMLLoader(getClass().getResource("/fxml/addNewProjectView.fxml"));

        // TODO - Remove 'inputWindow' and related code after implementation is done
        FXMLLoader inputWindow = new FXMLLoader(getClass().getResource("/fxml/inputView.fxml"));

        headerController = new HeaderController();
        sideBarController = new SideBarController();
        modalController = new ModalController();
        inputController = new InputController();
        addNewProjectController = new AddNewProjectController();

        header.setController(headerController);
        sideBar.setController(sideBarController);
        modalWindow.setController(modalController);
        addNewProject.setController(addNewProjectController);

        inputWindow.setController(inputController);

        Node headerNode = null;
        Node sideBarNode = null;
        Node modalWindowNode = null;
        Node inputWindowNode = null;
        Node addNewProjectNode = null;

        try {
            headerNode = header.load();
            sideBarNode = sideBar.load();
            modalWindowNode = modalWindow.load();
            addNewProjectNode = addNewProject.load();

            inputWindowNode = inputWindow.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        headerAnchorPane.getChildren().setAll(headerNode);
        setAnchors(headerAnchorPane, headerNode);
        sideBarAnchorPane.getChildren().setAll(sideBarNode);
        setAnchors(sideBarAnchorPane, sideBarNode);
        modalAnchorPane.getChildren().setAll(modalWindowNode);
        setAnchors(modalAnchorPane, modalWindowNode);
        addNewProjectAnchorPane.getChildren().setAll(addNewProjectNode);
        setAnchors(addNewProjectAnchorPane, addNewProjectNode);

        centerStageAnchorPane.getChildren().setAll(inputWindowNode);
        setAnchors(centerStageAnchorPane, inputWindowNode);

        loadProjects();
    }

    /**
     * Method for anchoring child to its parent.
     * @param anchorPane Parent.
     * @param node Child.
     */
    private void setAnchors(AnchorPane anchorPane, Node node) {
        anchorPane.setTopAnchor(node, 0.0);
        anchorPane.setRightAnchor(node, 0.0);
        anchorPane.setLeftAnchor(node, 0.0);
        anchorPane.setBottomAnchor(node, 0.0);
    }

    /**
     * Loads the projects existing in the save directory into the application.
     */
    private void loadProjects() {
        ArrayList<Project> projects = SaveManager.getInstance().readProjects();

        // If no projects were read then don't continue.
        if(projects.size() == 0) {
            return;
        }

        for (Project project : projects) {
            sideBarController.addNewSideBarItem(project.getName());
            Model.getInstance().addProject(project);      // Add project to 'projects' list during load.
        }

        // TODO - Remove code below...maybe? Depends on if a project should be 'chosen' on startup.
        ProjectManager.getInstance().setActiveProject(ProjectManager.getInstance().getProjects().get(0).getName());     // Sets first loaded project as the active project.
    }

    /**
     * Method is called when a button that wants to change view/scene is clicked in order to change view
     * @param fxmlName the name of the fxml file that should be changed to
     */
    @Override
    public void update(String fxmlName) {
        Node center = null;
        try {
            center = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        centerStageAnchorPane.getChildren().setAll(center);
    }

    /**
     * Getter for the modalAnchorPane.
     * @return
     */
    public AnchorPane getModalAnchorPane() {
        return modalAnchorPane;
    }

    public AnchorPane getAddNewProjectAnchorPane() { return addNewProjectAnchorPane; }

    public HeaderController getHeaderController() {
        return headerController;
    }

    public SideBarController getSideBarController() {
        return sideBarController;
    }

    public ModalController getModalController() {
        return modalController;
    }
}
