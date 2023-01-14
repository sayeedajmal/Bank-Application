package com.Bank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

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

/**
 * LoginController
 */
public class LoginController implements Initializable {
    public JFXTextField username = new JFXTextField();
    public JFXPasswordField password = new JFXPasswordField();
    public JFXButton test = new JFXButton();
    public Label label = new Label();

    @FXML
    public void Access(ActionEvent event) throws IOException, SQLException {
        if (username.getText().isBlank()) {
            label.setText("Fill UserName");
            return;
        }
        if (password.getText().isBlank()) {
            label.setText("Fill Password");
            return;
        }
        // Convert USRENAME TO String and PASSWORD to String
        String USERNAME = username.getText();
        String PASSWORD = password.getText();
        String user_path = System.getProperty("user.home") + File.separator + ".config";
        user_path += File.separator + "username";
        File user = new File(user_path + ".txt");
        FileWriter fileWriter = new FileWriter(user);
        fileWriter.append(USERNAME.toUpperCase());
        fileWriter.close();
        String authentic = "SELECT * FROM " + username.getText().toUpperCase() + " WHERE USERNAME = ? and PASSWORD = ?";
        try {
            Connection connection = DBConnect.Embadded();
            PreparedStatement preparedStatement = connection.prepareStatement(authentic);
            preparedStatement.setString(1, USERNAME);
            preparedStatement.setString(2, PASSWORD);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                scene.getStylesheets().add("/styles/Styles.css");
                Image icon = new Image("/Images/account.png");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Dashboard");
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.centerOnScreen();
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            } else {
                label.setText("UserName or Password is Invalid");
            }
        } catch (Exception e) {
            label.setText(e.getLocalizedMessage());
        }

    }

    public void signUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Signup.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        Image icon = new Image("/Images/enter.png");
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void close(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}