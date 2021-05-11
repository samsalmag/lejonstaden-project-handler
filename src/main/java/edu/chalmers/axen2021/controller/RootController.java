package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.controller.items.ItemType;
import edu.chalmers.axen2021.controller.main.HeaderController;
import edu.chalmers.axen2021.controller.main.InputController;
import edu.chalmers.axen2021.controller.main.SideBarController;
import edu.chalmers.axen2021.controller.main.SummaryViewController;
import edu.chalmers.axen2021.controller.modals.AddNewCostController;
import edu.chalmers.axen2021.controller.modals.AddNewProjectController;
import edu.chalmers.axen2021.controller.modals.ConfirmationController;
import edu.chalmers.axen2021.controller.modals.ModalController;
import edu.chalmers.axen2021.model.Category;
import edu.chalmers.axen2021.model.managers.PdfManager;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import edu.chalmers.axen2021.model.managers.SaveManager;
import edu.chalmers.axen2021.model.projectdata.CostItem;
import edu.chalmers.axen2021.model.projectdata.Project;
import edu.chalmers.axen2021.view.AXEN2021;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


/**
 * Controller singleton class for the applications root.fxml.
 * Initialize starting page and all its nodes.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
public class RootController {

    /**
     * Instance of RootController class.
     */
    private static RootController instance = null;

    private ProjectManager projectManager = ProjectManager.getInstance();
    private SaveManager saveManager = SaveManager.getInstance();

    // Controllers for the views.
    private HeaderController headerController;
    private SideBarController sideBarController;
    private ModalController modalController;
    private AddNewProjectController addNewProjectController;
    private AddNewCostController addNewCostController;
    private SummaryViewController summaryViewController;
    private InputController inputController;
    private ConfirmationController confirmationController;

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

    @FXML private AnchorPane confirmationAnchorPane;

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
        inputController = new InputController();
        summaryViewController = new SummaryViewController();
        confirmationController = new ConfirmationController();

        // Init the fxml code.
        initFXML(headerAnchorPane, "header.fxml", headerController);
        initFXML(sideBarAnchorPane, "sideBar.fxml", sideBarController);
        initFXML(modalAnchorPane, "modalWindow.fxml", modalController);
        initFXML(addNewProjectAnchorPane, "addNewProjectView.fxml", addNewProjectController);
        initFXML(addNewCostAnchorPane, "addNewCostView.fxml", addNewCostController);
        inputWindowNode = initFXML(centerStageAnchorPane, "inputView.fxml", inputController);
        summaryViewNode = initFXML(centerStageAnchorPane, "summaryView.fxml", summaryViewController);
        initFXML(confirmationAnchorPane,"confirmationView.fxml", confirmationController);

        defaultCenterStageAnchorPane.toFront();

        // Load all projects from save files and create views for them.
        initLoadedProjects();
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
     * Initializes all saved projects by loading them and creating vewis for them
     */
    private void initLoadedProjects() {
        ProjectManager.getInstance().loadProjects();
        sideBarController.populateProjectButtons();
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

        inputController.clearApartmentItems();
        inputController.populateApartmentItems();

        inputWindowNode.toFront();
    }

    /**
     * Puts current projects summaryView to front in centerStage.
     */
    public void updateSummaryView(){
        summaryViewController.updateTextFields();
        summaryViewController.updateTitle();

        summaryViewController.clearApartmentItems();
        summaryViewController.populateApartmentItems();

        summaryViewNode.toFront();
    }

    public void updateAllLabels(){
        projectManager.getActiveProject().updateAllVariables();
        inputController.updateAllTextFields();
        summaryViewController.updateTextFields();
    }

    /**
     * Starts the 'create PDF' process.
     * Opens a dialog window to chose save directory.
     * Creates a PDF if a directory is chosen.
     */
    public void createPdf(String projectName) {
        Project project = projectManager.getProject(projectName);
        PdfManager.getInstance().makePdf(project);
    }

    /**
     * Remove CostItem and reload new view.
     * @param costItemName Name of cost item to remove.
     */
    public void removeCostItem(String costItemName){
        projectManager.getActiveProject().removeCostItem(projectManager.getActiveCategory(), costItemName);
        modalController.clearCostItems();
        modalController.populateCostItems();
        saveProjectData();
    }

    public void removeApartmentItem(ApartmentItem item){
        projectManager.getActiveProject().removeApartmentItem(item);
        inputController.clearApartmentItems();
        inputController.populateApartmentItems();
        summaryViewController.clearApartmentItems();
        summaryViewController.populateApartmentItems();
        updateAllLabels();  // Should update labels and variables after an apartmentItem is removed.
        saveProjectData();
    }

    public void removeProject(String projectName){
        projectManager.removeProject(projectName);
        sideBarController.clearAllProjectButtons();
        sideBarController.populateProjectButtons();
        if(projectName.equals(projectManager.getActiveProject().getName())){
            projectManager.setActiveProject(null);
            defaultCenterStageAnchorPane.toFront();
        } else {
            sideBarController.setActiveButton(projectManager.getActiveProject().getName());
        }
    }

    /**
     * Creates a new project (sidebarItem) and adds it to the SideBar.
     * Also creates a save-file for the project.
     */
    public void addProject(String projectName){
        sideBarController.addNewSideBarItem(projectName);
        new Project(projectName);
        projectManager.setActiveProject(projectName);
        saveProjectData();
        setActiveProjectButton(projectName);
    }

    /**
     * Method for adding a new cost item.
     * Adds modalWindowItem to the TilePane in modalWindow.
     */
    public void addCostItem(String name) {
        CostItem newCostItem = projectManager.getActiveProject().addCostItem(name);
        modalController.addModalItem(newCostItem);
        saveProjectData();
    }

    public void setActiveProjectButton(String projectName){
        sideBarController.setActiveButton(projectName);
    }

    /**
     * Save all data in the project.
     */
    public void saveProjectData(){
        saveManager.saveProject(projectManager.getActiveProject());
        saveManager.saveProjectManager();
    }

    /**
     * Method for opening the modalWindow for a category.
     * Puts the modalWindowAnchorPane to front in scene.
     * @param clickedCategory The category of the clicked button.
     */
    public void openModalWindow(Category clickedCategory) {
        ProjectManager.getInstance().setActiveCategory(clickedCategory);
        modalController.setCategoryNameLabelText(clickedCategory.getName());
        modalController.populateCostItems();
        modalAnchorPane.toFront();
    }

    /**
     * Method for closing the modalWindow.
     * Puts the modalWindowAnchorPane back in the scene.
     */
    public void closeModalWindow() {
        saveManager.saveProject(projectManager.getActiveProject());
        projectManager.getActiveProject().updateAllVariables();
        updateInputView();
        modalAnchorPane.toBack();
        modalController.clearCostItems();
    }

    public void openConfirmationView(String nameObjectToRemove, ItemType type){
        confirmationController.setItemToRemove(nameObjectToRemove, type);
        confirmationController.setEventHandler(type);
        confirmationAnchorPane.toFront();
    }

    public void openConfirmationView(ApartmentItem item, ItemType type){
        confirmationController.setItemToRemove(item, type);
        confirmationController.setEventHandler(type);
        confirmationAnchorPane.toFront();
    }

    public void closeConfirmationView(){
        confirmationAnchorPane.toBack();
    }

    public void focusTextField(ItemType type) {

        // Focus on the TextField for the given type.
        if (type == ItemType.PROJECT) {
            addNewProjectController.getProjectNameTextField().requestFocus();

        } else if(type == ItemType.COST_ITEM) {
            addNewCostController.getCostNameTextField().requestFocus();
        }
    }

    /**
     * Opens the addNewProject view.
     */
    public void openAddNewProjectView() {
        addNewProjectAnchorPane.toFront();
        focusTextField(ItemType.PROJECT);   // Focus the text field for project name input.
    }

    /**
     * Method for closing the addNewProjectView.
     * Puts the addNewProjectView to back in scene.
     */
    public void closeAddNewProjectView() {
        addNewProjectAnchorPane.toBack();
        addNewProjectController.getProjectNameTextField().clear();
    }

    /**
     * Opens the addNewCostView based on addNewCostView.fxml.
     */
    public void openAddNewCostView() {
        addNewCostAnchorPane.toFront();
        focusTextField(ItemType.COST_ITEM);   // Focus the text field for cost item name input.
    }

    /**
     * Method for closing the addNewCostView.
     * Puts the addNewCostView to back in scene.
     */
    public void closeAddNewCostView() {
        addNewCostAnchorPane.toBack();
        addNewCostController.getCostNameTextField().clear();
    }
}
