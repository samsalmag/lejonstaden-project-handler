package edu.chalmers.axen2021.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AXEN2021 extends Application {

    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent rootFXML = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/root.fxml"));
        Scene scene = new Scene(rootFXML, 1280, 720);

        stage.setScene(scene);
        stage.setFullScreen(true);
        String title = "AXE-N 2021";
        stage.setTitle(title);
        stage.show();
    }
}
