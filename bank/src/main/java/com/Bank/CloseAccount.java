package com.Bank;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CloseAccount implements Initializable {
    public Label NAME = new Label();
    public Label NUMBER = new Label();
    public Label AMMOUNT = new Label();
    public Label IFSC = new Label();

    @FXML
    public void deleteaccount(ActionEvent event) throws InterruptedException {
        Connection connection;
        try {
            connection = DBConnect.Embadded();
            Statement statement = connection.createStatement();
            String query = "Drop Table SAYEED";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Done! It...");

        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void No(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       /*  try {
            Connection connection = DBConnect.Embadded();
            String query = "SELECT * FROM SAYEED";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                NAME.setText(resultSet.getString("username"));

                Timeline FetchingAmmount = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                    try {
                        AMMOUNT.setText(resultSet.getString("ammount"));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }), new KeyFrame(Duration.seconds(1)));
                FetchingAmmount.setCycleCount(Animation.INDEFINITE);
                FetchingAmmount.play();

                IFSC.setText(resultSet.getString("ifsc"));
                NUMBER.setText(resultSet.getString("account"));
            } else {
                System.out.println("Got IT...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } */

    }

}
