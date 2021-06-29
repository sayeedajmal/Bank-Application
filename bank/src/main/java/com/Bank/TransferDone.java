package com.Bank;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TransferDone implements Initializable {
    public Label NAME = new Label();
    public Label NUMBER = new Label();
    public Label AMMOUNT = new Label();
    public JFXButton DoneTransfer = new JFXButton();

    @FXML
    public void DoneTransfer(ActionEvent event) {
        System.out.println("Hello! You Clicked");
    }

    @FXML
    public void No(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
