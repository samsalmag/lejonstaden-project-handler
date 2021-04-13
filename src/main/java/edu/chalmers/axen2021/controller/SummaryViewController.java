package edu.chalmers.axen2021.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class SummaryViewController {

    private Node SummaryView;

    public SummaryViewController() {

        FXMLLoader fxmlSummary = new FXMLLoader(getClass().getResource("/fxml/SummaryView.fxml"));
        fxmlSummary.setController(this);
        try {
            SummaryView = fxmlSummary.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Node getNode() {
        return SummaryView;
    }
}
