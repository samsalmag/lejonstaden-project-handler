package edu.chalmers.axen2021.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class SummaryViewController {

    /**
     * Node of the SummaryView.fxml
     */
    private Node SummaryView;

    /**
     * Initialize SummaryView.fxml as a Node
     */
    public SummaryViewController() {

        FXMLLoader fxmlSummary = new FXMLLoader(getClass().getResource("/fxml/SummaryView.fxml"));
        fxmlSummary.setController(this);
        try {
            SummaryView = fxmlSummary.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for SummaryView Node
     * @return SummaryView as a Node
     */
    public Node getNode() {
        return SummaryView;
    }
}
