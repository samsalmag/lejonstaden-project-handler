package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.managers.ProjectManager;
import edu.chalmers.axen2021.observers.IViewObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


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
    private AddNewProjectController addNewProjectController;
    private AddNewCostController addNewCostController;

    private SummaryViewController summaryViewController;
    private InputController inputController;

    //Private accessible nodes.
    private Node inputWindowNode = null;
    private Node summaryViewNode = null;

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
     * AnchorPane for addNewCostView.fxml in root.fxml
     */
    @FXML private AnchorPane addNewCostAnchorPane;

    /**
     * AnchorPane for default background of centerStage AnchorPane.
     */
    @FXML private AnchorPane defaultCenterStageAnchorPane;

    /**
     * Initialize method that starts up the first scene and all its children.
     */
    public void initialize() {

        // Init all controllers.
        headerController = new HeaderController();
        sideBarController = new SideBarController();
        modalController = new ModalController();
        addNewProjectController = new AddNewProjectController();
        addNewCostController = new AddNewCostController();

        // Init the fxml code.
        initFXML(headerAnchorPane, "header.fxml", headerController);
        initFXML(sideBarAnchorPane, "sideBar.fxml", sideBarController);
        initFXML(modalAnchorPane, "modalWindow.fxml", modalController);
        initFXML(addNewProjectAnchorPane, "addNewProjectView.fxml", addNewProjectController);
        initFXML(addNewCostAnchorPane, "addNewCostView.fxml", addNewCostController);

        // TODO - remove lines below and associated values later..?
        inputController = new InputController();
        summaryViewController = new SummaryViewController();
        inputWindowNode = initFXML(centerStageAnchorPane, "inputView.fxml", inputController);
        summaryViewNode = initFXML(centerStageAnchorPane, "summaryView.fxml", summaryViewController);

        defaultCenterStageAnchorPane.toFront();
        ProjectManager.getInstance().loadProjects();
    }

    /**
     * Initializes and loads an fxml file.
     * Attaches the newly created node to 'anchorPane' and uses 'controller' as the Controller.
     * @param anchorPane The anchor pane to attach the newly created node to.
     * @param fxmlName Name of the .fxml file.
     * @param controller The controller for the node.
     * @return The node created from the .fxml file
     */
    private Node initFXML(AnchorPane anchorPane, String fxmlName, Object controller) {

        // Give exception if given class is not a controller (not annotated with @FXMLController).
        if(!controller.getClass().isAnnotationPresent(FXMLController.class)) {
            throw new IllegalArgumentException(controller + " is not a controller class! (not annotated with @FXMLController)");
        }

        // Load fxml and create a node for it.
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/" + fxmlName));
        fxml.setController(controller);
        Node fxmlNode = null;
        try {
            fxmlNode = fxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Anchor the node to its anchor pane.
        anchorPane.getChildren().add(fxmlNode);
        setAnchors(anchorPane, fxmlNode);

        return fxmlNode;
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
     * Puts current projects inputView to front in centerStage.
     */
    public void updateInputView(){
        inputController.updateAllTextFields();
        inputController.updateTitle();
        inputWindowNode.toFront();
    }

    /**
     * Puts current projects summaryView to front in centerStage.
     */
    public void updateSummaryView(){
        summaryViewController.updateTextFields();
        summaryViewController.updateTitle();
        summaryViewNode.toFront();
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

    public AnchorPane getAddNewCostAnchorPane() {
        return addNewCostAnchorPane;
    }
}
