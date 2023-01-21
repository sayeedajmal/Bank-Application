package com.Bank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DBConnect {

    public static Connection Embadded() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/BANK", "root", "PERSONAL");
        return connection;
    }

    public void Connect(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
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

    public void No(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
    /*
     * public static void main(String[] args) throws SQLException {
     * Connection connection = Embadded();
     * String query = "SELECT * FROM SAYEED";
     * Statement preparedStatement = connection.createStatement();
     * preparedStatement.executeQuery(query);
     * ResultSet resultSet = preparedStatement.getResultSet();
     * if (resultSet.next()) {
     * System.out.println(resultSet.getString("username"));
     * System.out.println(resultSet.getString("birthdate"));
     * System.out.println(resultSet.getString("gender"));
     * System.out.println(resultSet.getString("account"));
     * System.out.println(resultSet.getString("ifsc"));
     * System.out.println(resultSet.getString("email"));
     * System.out.println(resultSet.getString("phone"));
     * System.out.println(resultSet.getString("password"));
     * System.out.println(resultSet.getString("ammount"));
     * } else {
     * 
     * }
     * }
     */
}
