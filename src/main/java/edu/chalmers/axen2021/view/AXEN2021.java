package edu.chalmers.axen2021.view;

import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.model.managers.SaveManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main view class for the application.
 * @author Sam Salek
 * @author Oscar Arvidson
 */
public class AXEN2021 extends Application {

    private static Stage mainStage;

    /**
     * Runs the view configured in the 'start' method.
     * @param args
     */
    public static void run(String[] args) {
        launch(args);
    }

    /**
     * Applies and configures the main root view.
     * @param stage The main window.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/root.fxml"));
        RootController rootController = RootController.getInstance();
        fxmlLoader.setController(rootController);
        Parent rootFXML = fxmlLoader.load();

        Scene scene = new Scene(rootFXML, 1366, 768);
        stage.setScene(scene);
        stage.setFullScreen(true);
        String title = "AXE-N 2021";
        stage.setTitle(title);
        stage.show();

        mainStage = stage;
        initShutdownHook();
    }

    /**
     * Initializes the Shutdown Hook AKA what should happen on application shutdown.
     */
    private void initShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(ProjectManager.getInstance().getActiveProject() != null) {
                SaveManager.getInstance().saveProject(ProjectManager.getInstance().getActiveProject());
            }
        }));
    }

    /**
     * This method terminates the application.
     */
    public static void terminate() {
        Platform.exit();
    }

    /**
     * Returns the main stage for this application.
     * @return Main stage.
     */
    public static Stage getMainStage() {
        return mainStage;
    }
}
