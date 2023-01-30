package com.Bank;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.jfoenix.controls.JFXTextField;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class deposite implements Initializable {
    public JFXTextField depositeammount;
    public Label NAME = new Label();
    public Label NUMBER = new Label();
    public Label AMMOUNT = new Label();
    public Label IFSC = new Label();
    Stage window = new Stage();
    String uname;

    public void Donebutton(ActionEvent event) throws IOException, InterruptedException {
        String user_path = System.getProperty("user.home") + File.separator + ".config";
        user_path += File.separator + "username";
        File user = new File(user_path + ".txt");
        try {
            Connection connection = DBConnect.Embadded();
            /* Fetching the Ammount */
            String query = "SELECT * FROM " + uname.toUpperCase();
            Integer ammount;
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                ammount = resultSet.getInt("AMMOUNT");
                String Account = resultSet.getString("account");
                Scanner input = new Scanner(user);
                while (input.hasNext()) {

                    String update_ammount = depositeammount.getText();
                    int change_int = Integer.parseInt(update_ammount);

                    uname = input.next();
                    ammount += change_int;

                    String Query = "UPDATE " + uname.toUpperCase() + " SET AMMOUNT=" + ammount + " WHERE ACCOUNT ="
                            + Account;
                    PreparedStatement preparedStatement = connection.prepareStatement(Query);
                    preparedStatement.executeUpdate(Query);
                    No(event);
                    System.out.println("Yes..Done Deposite");
                }
                input.close();
            }

        } catch (Exception e) {
            e.getLocalizedMessage();
        }

    }

    @FXML
    public void No(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void fetch() {
        String user_path = System.getProperty("user.home") + File.separator + ".config";
        user_path += File.separator + "username";
        File file = new File(user_path + ".txt");
        try {
            Connection connection = DBConnect.Embadded();
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                uname = input.next();
                String query = "select * from " + uname.toUpperCase();
                Statement statement = connection.createStatement();
                statement.execute(query);
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    NAME.setText(resultSet.getString("username"));
                    NUMBER.setText(resultSet.getString("account"));
                    IFSC.setText(resultSet.getString("ifsc"));
                    Timeline FetchingAmmount = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                        try {
                            AMMOUNT.setText(resultSet.getString("ammount"));
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }), new KeyFrame(Duration.seconds(1)));
                    FetchingAmmount.setCycleCount(Animation.INDEFINITE);
                    FetchingAmmount.play();
                } else {
                    System.out.println("I can't Think About That");
                }
            }
            input.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetch();
    }

}
