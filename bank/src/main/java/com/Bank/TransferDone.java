package com.Bank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class TransferDone implements Initializable {
    public Label NAME = new Label();
    public Label NUMBER = new Label();
    public Label AMMOUNT = new Label();
    public JFXButton DoneTransfer = new JFXButton();
    public JFXTextField temp_name = new JFXTextField();
    public JFXTextField temp_number = new JFXTextField();
    public JFXTextField temp_ifsc = new JFXTextField();
    public JFXTextField temp_number1 = new JFXTextField();
    public Label information = new Label();
    String uname;

    @FXML
    public void Ready(ActionEvent event) throws IOException {
        /* getting Username from user to fetch the data of database */
        String String_name = temp_name.getText();
        this.uname = String_name;
        System.out.println("-----------------------------");
        System.out.println(uname);
        Connection connection;
        try {
            connection = DBConnect.Embadded();

            String Query = "SELECT * FROM " + String_name.toUpperCase();

            Statement statement = connection.createStatement();
            statement.executeQuery(Query);

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                /* Getting information of account via Database */
                String match_name = resultSet.getString("username");
                String match_account = resultSet.getString("account");
                String match_ifsc = resultSet.getString("ifsc");

                /* Getting information from User */
                String user_name = temp_name.getText();
                String user_ifsc = temp_ifsc.getText();
                String user_account = temp_number.getText();
                String user_account1 = temp_number1.getText();
                if (user_account.equals(user_account1) && match_account.equals(user_account)) {
                    if (match_name.equals(user_name)) {
                        if (match_ifsc.equals(user_ifsc)) {
                            Stage stage = new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("/fxml/TransferDone.fxml"));
                            Scene scene = new Scene(root);
                            scene.getStylesheets().add("/styles/Styles.css");
                            Image Icon = new Image("/Images/user.png");
                            stage.getIcons().add(Icon);
                            stage.setTitle("Transfer");
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.centerOnScreen();
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.showAndWait();
                            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                        } else {
                            information.setText("IFSC code not Matched");
                            temp_ifsc.setText(null);
                            return;
                        }
                    } else {
                        information.setText("USERNAME not Matched");
                        temp_name.setText(null);
                        return;
                    }
                } else {
                    information.setText("Account Number not Matched");
                    temp_number1.setText(null);
                    return;
                }
            }
        } catch (SQLException e) {
            information.setText("Provided Infromation Doesn't Exist");
            temp_name.setText(null);
            temp_ifsc.setText(null);
            temp_number.setText(null);
            temp_number1.setText(null);
        }
    }

    public void DoneTransfer(ActionEvent event) {
        System.out.println("Hello! You Clicked");
        System.out.println(uname);

    }

    @FXML
    public void No(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("fuck it");
        }
    }

}
