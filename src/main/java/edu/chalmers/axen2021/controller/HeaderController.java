package edu.chalmers.axen2021.controller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.io.IOException;


public class HeaderController{

    //FXML Variables
    @FXML private ImageView logoImageView;

    //Variables
    private Node header;

    public HeaderController(){
        FXMLLoader fxmlHeader = new FXMLLoader(getClass().getResource("/fxml/header.fxml"));
        fxmlHeader.setController(this);

        try {
            header = fxmlHeader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Node getHeader() {
        return header;
    }
}
