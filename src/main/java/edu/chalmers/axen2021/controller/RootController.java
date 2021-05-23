package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.controller.items.ApartmentItemController;
import edu.chalmers.axen2021.controller.items.ApartmentItemControllerFactory;
import edu.chalmers.axen2021.controller.items.ItemType;
import edu.chalmers.axen2021.controller.main.HeaderController;
import edu.chalmers.axen2021.controller.main.InputController;
import edu.chalmers.axen2021.controller.main.SideBarController;
import edu.chalmers.axen2021.controller.main.SummaryViewController;
import edu.chalmers.axen2021.controller.modals.*;
import edu.chalmers.axen2021.model.Category;
import edu.chalmers.axen2021.model.managers.PdfManager;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import edu.chalmers.axen2021.model.managers.SaveManager;
import edu.chalmers.axen2021.model.projectdata.CostItem;
import edu.chalmers.axen2021.model.projectdata.Project;
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
 * @author Sam Salek
 */
public class RootController {

    /**
     * Instance of RootController class.
     */
    private static RootController instance = null;

    /**
     * The project manager. Handles the projects.
     */
    private final ProjectManager projectManager = ProjectManager.getInstance();

    /**
     * The save manager. Handles everything about saving.
     */
    private final SaveManager saveManager = SaveManager.getInstance();

    // Controllers for the views.
    private HeaderController headerController;
    private SideBarController sideBarController;
    private ModalController modalController;
    private AddNewProjectController addNewProjectController;
    private AddNewCostController addNewCostController;
    private SummaryViewController summaryViewController;
    private InputController inputController;
    private ConfirmationController confirmationController;
    private ChangeProjectNameController changeProjectNameController;
    private ChangeCostItemNameController changeCostItemNameController;

    // Private accessible nodes.
    // Need these to be able to switch between the two views (input and summary).
    private Node inputWindowNode = null;
    private Node summaryViewNode = null;

    // Because of singleton pattern.
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
     * AnchorPane for confirmationView.fxml in root.fxml
     */
    @FXML private AnchorPane confirmationAnchorPane;

    /**
     * AnchorPane for changeProjectName.fxml in root.fxml
     */
    @FXML private AnchorPane changeProjectNameAnchorPane;

    /**
     * AnchorPane for changeCostItemName.fxml in root.fxml
     */
    @FXML private AnchorPane changeCostItemNameAnchorPane;

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
        changeProjectNameController = new ChangeProjectNameController();
        changeCostItemNameController = new ChangeCostItemNameController();

        // Init the fxml code.
        initFXML(headerAnchorPane, "header.fxml", headerController);
        initFXML(sideBarAnchorPane, "sideBar.fxml", sideBarController);
        initFXML(modalAnchorPane, "modalWindow.fxml", modalController);
        initFXML(addNewProjectAnchorPane, "addNewProjectView.fxml", addNewProjectController);
        initFXML(addNewCostAnchorPane, "addNewCostView.fxml", addNewCostController);
        inputWindowNode = initFXML(centerStageAnchorPane, "inputView.fxml", inputController);
        summaryViewNode = initFXML(centerStageAnchorPane, "summaryView.fxml", summaryViewController);
        initFXML(confirmationAnchorPane,"confirmationView.fxml", confirmationController);
        initFXML(changeProjectNameAnchorPane, "changeProjectNameView.fxml", changeProjectNameController);
        initFXML(changeCostItemNameAnchorPane, "changeCostItemNameView.fxml", changeCostItemNameController);

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
        setAnchors(fxmlNode);

        return fxmlNode;
    }

    /**
     * Initializes all saved projects by loading them and creating views for them.
     */
    private void initLoadedProjects() {
        ProjectManager.getInstance().loadProjects();
        sideBarController.populateProjectButtons();
    }

    /**
     * Method for anchoring child to its parent.
     * @param node Child.
     */
    private void setAnchors(Node node) {
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
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

    /**
     * Updates the views for the apartment items by removing them all and then recreating them.
     */
    public void updateApartmentItemsViews() {
        // Only clear and populate the apartment item views of the front view.
        // Cuts the apartment item views created in half.
        if(isSummaryViewInFront()) {
            summaryViewController.clearApartmentItems();
            summaryViewController.populateApartmentItems();
        } else if(isInputViewInFront()) {
            inputController.clearApartmentItems();
            inputController.populateApartmentItems();
        }
    }

    /**
     * Updates all variable TextFields in all apartment items in the current project.
     */
    public void updateApartmentItemsValues() {
        // Go through all active instances of ApartmentItemController and call method to update values.
        for(ApartmentItemController aic : ApartmentItemControllerFactory.getInstances()) {
            aic.updateAllDisplayValues();
        }
    }

    /**
     * Updates all variable labels in the input and summary views.
     */
    public void updateAllLabels(){
        projectManager.getActiveProject().updateAllVariables();
        inputController.updateAllTextFields();
        summaryViewController.updateTextFields();
        updateApartmentItemsValues();
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
     * Renames a project.
     * @param currentName The current name of the project.
     * @param newName The new name of the project.
     */
    public void renameProject(String currentName, String newName) {
        Project project = projectManager.getProject(currentName);

        // Try to remove old save file. If successful then change name.
        if(saveManager.removeProjectFile(project)) {
            project.setName(newName);

            // Update title only if there is a active project (throws exception if they are called while null)
            if(projectManager.getActiveProject() != null) {
                summaryViewController.updateTitle();
                inputController.updateTitle();
            }

            saveManager.saveProject(project);
            sideBarController.clearAllProjectButtons();
            sideBarController.populateProjectButtons();

            // Update project toggle button only if there is a active project (throws exception if it's' called while null)
            if(projectManager.getActiveProject() != null) {
                sideBarController.setActiveButton(projectManager.getActiveProject().getName());
            }

        }
    }

    /**
     * Renames a cost item in all projects and in the active category.
     * @param currentName The current name of the cost item.
     * @param newName The new name of the cost item.
     */
    public void renameCostItem(String currentName, String newName) {
        projectManager.changeCostItemName(projectManager.getActiveCategory(), currentName, newName);
        modalController.clearCostItems();
        modalController.populateCostItems();
        saveAllProjectData();  // Save data for all projects. Values are otherwise lost in all inactive projects.
    }

    /**
     * Removes a cost item from the active project and reloads modal view.
     * @param costItemName Name of cost item to be removed.
     */
    public void removeCostItem(String costItemName){
        projectManager.getActiveProject().removeCostItem(projectManager.getActiveCategory(), costItemName);
        modalController.clearCostItems();
        modalController.populateCostItems();
        saveProjectData();
    }

    /**
     * Removes an apartment item from the active project and reloads the lagenhets view.
     * @param apartmentItem The apartment item to be removed.
     */
    public void removeApartmentItem(ApartmentItem apartmentItem){
        projectManager.getActiveProject().removeApartmentItem(apartmentItem);
        updateApartmentItemsViews();
        updateAllLabels();  // Should update labels and variables after an apartmentItem is removed.
        saveProjectData();
    }

    /**
     * Removes a project from the application and reloads the side bar view.
     * @param projectName Name of the project to be removed.
     */
    public void removeProject(String projectName){
        projectManager.removeProject(projectName);
        sideBarController.clearAllProjectButtons();
        sideBarController.populateProjectButtons();

        // If there is no active project: only bring center stage to front.
        if(projectManager.getActiveProject() == null) {
            defaultCenterStageAnchorPane.toFront();
        }
        // If the active project is the project being removed: set active project to null and bring center stage to front.
        else if(projectName.equals(projectManager.getActiveProject().getName())){
            projectManager.setActiveProject(null);
            defaultCenterStageAnchorPane.toFront();
        }
        // If there is a active project but it is not the project being removed: Toggle the active project's button.
        else {
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

    /**
     * Sets the active project button.
     * @param projectName The project name.
     */
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
     * Saves the data for all existing projects.
     */
    public void saveAllProjectData() {
        for(Project project : projectManager.getProjects()) {
            saveManager.saveProject(project);
        }
        saveManager.saveProjectManager();
    }

    /**
     * Method for opening the modalWindow for a category.
     * Puts the modalWindowAnchorPane to front in scene.
     * @param clickedCategory The category of the clicked button.
     */
    public void openModalWindow(Category clickedCategory) {
        ProjectManager.getInstance().setActiveCategory(clickedCategory);
        modalController.setCategoryNameLabelText(clickedCategory.getDisplayName());
        modalController.populateCostItems();
        modalAnchorPane.toFront();
        modalController.getModalWindowItemVBox().requestFocus(); // Sets focus to modal window. Is needed for onKeyPressed events.
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

    /**
     * Opens the confirmation view. Sets a cost item or project as the object to be removed.
     * @param nameObjectToRemove Name of cost item / project to be removed.
     * @param type ItemType of the object to be removed (cost item / project)
     */
    public void openConfirmationView(String nameObjectToRemove, ItemType type){
        confirmationController.setItemToRemove(nameObjectToRemove, type);
        confirmationAnchorPane.toFront();
        confirmationController.getMainVBox().requestFocus();    // Enables keyEvents.
    }

    /**
     * Opens the confirmation view. Sets an apartment item as the object to be removed.
     * @param apartmentItem The apartment item to be removed.
     */
    public void openConfirmationView(ApartmentItem apartmentItem){
        confirmationController.setItemToRemove(apartmentItem);
        confirmationAnchorPane.toFront();
        confirmationController.getMainVBox().requestFocus();    // Enables keyEvents.
    }

    /**
     * Closes the confirmation view.
     */
    public void closeConfirmationView(){
        confirmationAnchorPane.toBack();

        // Returns focus to modal window. Is needed for onKeyPressed events.
        // (only needed when closing confirmation view when removing a cost item)
        // Doesn't seem to affect the view when removing a project or apartment item.
        modalController.getModalWindowItemVBox().requestFocus();
    }

    /**
     * Opens the addNewProject view.
     */
    public void openAddNewProjectView() {
        addNewProjectAnchorPane.toFront();
        addNewProjectController.getProjectNameTextField().requestFocus();   // Focus the text field for project name input.
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
        addNewCostController.getCostNameTextField().requestFocus();   // Focus the text field for cost item name input.
    }

    /**
     * Method for closing the addNewCostView.
     * Puts the addNewCostView to back in scene.
     */
    public void closeAddNewCostView() {
        addNewCostAnchorPane.toBack();
        addNewCostController.getCostNameTextField().clear();
        modalController.getModalWindowItemVBox().requestFocus();  // Returns focus to modal window. Is needed for onKeyPressed events.
    }

    /**
     * Opens the changeProjectNameView based on changeProjectName.fxml.
     */
    public void openChangeProjectNameView(String currentProjectName) {
        changeProjectNameAnchorPane.toFront();
        changeProjectNameController.setCurrentProjectName(currentProjectName);
        changeProjectNameController.getProjectNameTextField().setText(currentProjectName);
        changeProjectNameController.getProjectNameTextField().requestFocus(); // Focus the text field for project name input.
    }

    /**
     * Method for closing the changeProjectNameView. Puts the changeProjectNameView to back in the scene.
     */
    public void closeChangeProjectNameView() {
        changeProjectNameAnchorPane.toBack();
    }

    /**
     * Opens the changeCostItemNameView based on changeCostItemNameView.fxml.
     */
    public void openChangeCostItemNameView(String currentCostItemName) {
        changeCostItemNameAnchorPane.toFront();
        changeCostItemNameController.setCurrentCostItemName(currentCostItemName);
        changeCostItemNameController.getCostNameTextField().setText(currentCostItemName);
        changeCostItemNameController.getCostNameTextField().requestFocus(); // Focus the text field for project name input.
    }

    /**
     * Method for closing the changeCostItemNameView. Puts the changeCostItemNameView to back in the scene.
     */
    public void closeChangeCostItemNameView() {
        changeCostItemNameAnchorPane.toBack();
        modalController.getModalWindowItemVBox().requestFocus();  // Returns focus to modal window. Is needed for onKeyPressed events.
    }

    /**
     * Checks if the InputView is in front.
     * @return True or False.
     */
    public boolean isInputViewInFront() {
        // The node in the views front is always the last child in the list.
        // Check if the last child node is the inputWindowNode.
        return centerStageAnchorPane.getChildren().get(centerStageAnchorPane.getChildren().size() - 1) == inputWindowNode;
    }

    /**
     * Checks if the SummaryView is in front.
     * @return True or False.
     */
    public boolean isSummaryViewInFront() {
        // The node in the views front is always the last child in the list.
        // Check if the last child node is the summaryViewNode.
        return centerStageAnchorPane.getChildren().get(centerStageAnchorPane.getChildren().size() - 1) == summaryViewNode;
    }
}
