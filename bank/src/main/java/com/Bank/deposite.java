package com.Bank;

import java.io.File;
import java.io.FileNotFoundException;
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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
        String pass_path = System.getProperty("user.home") + File.separator + ".config";
        pass_path += File.separator + "password";
        File Pass = new File(pass_path + ".txt");
        try {
            Scanner password = new Scanner(Pass);
            while (password.hasNext()) {
                Connection connection = DBConnect.Embadded();
                /* Fetching the Ammount */
                String query = "SELECT AMMOUNT FROM " + uname.toUpperCase();
                Integer ammount;
                Statement statement = connection.createStatement();
                statement.executeQuery(query);
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    ammount = resultSet.getInt("AMMOUNT");
                    /* Adding The Money */
                    String Password = password.next();
                    Scanner input = new Scanner(user);
                    while (input.hasNext()) {
                        uname = input.next();
                        String fetch = "update " + uname.toUpperCase() + " set ammount=? WHERE password='" + Password
                                + " '";
                        String update_ammount = depositeammount.getText();
                        int change_int = Integer.parseInt(update_ammount);
                        int adding = ammount + change_int;
                        PreparedStatement preparedStatement = connection.prepareStatement(fetch);
                        preparedStatement.setInt(1, adding);
                        preparedStatement.executeUpdate();
                        No(event);
                    }
                    input.close();
                }

            }
            password.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Deposited Your Ammount");
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
                    AMMOUNT.setText(resultSet.getString("ammount"));
                    IFSC.setText(resultSet.getString("ifsc"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetch();
    }

}
