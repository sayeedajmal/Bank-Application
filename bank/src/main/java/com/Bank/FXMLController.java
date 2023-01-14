package com.Bank;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLController implements Initializable {
    public AnchorPane dashpane;
    public JFXTextField username = new JFXTextField();
    public JFXTextField login_username = new JFXTextField();
    public JFXDatePicker birthdate = new JFXDatePicker();
    public JFXComboBox<String> gender = new JFXComboBox<>();
    public JFXTextField email = new JFXTextField();
    public JFXTextField phone = new JFXTextField();
    public JFXPasswordField password = new JFXPasswordField();
    public JFXPasswordField login_password = new JFXPasswordField();
    public JFXTextField account = new JFXTextField();
    public JFXTextField ifsc = new JFXTextField();
    public Label notfound;

    /* THIS SECTION IS FOR GETTING CONNECTION FROM MYSQL SERVER */
    Connection connection = DBConnect.Embadded();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public FXMLController() throws SQLException, ClassNotFoundException {
        connection = DBConnect.Embadded();
    }


    /* THIS SECTION IS FOR LOADING THE REGISTER PAGE */
    @FXML
    public void signup(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Signup.fxml"));
        dashpane.getChildren().setAll(pane);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("SignUp");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
    }


    /* CREATING METHOD FOR KNOWING "USERs" TABLE ARE EXIST ARE NOT */
    public static boolean doesTableExists(String tableName, Connection connection) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName.toUpperCase(), null);
        return resultSet.next();
    }

    @FXML
    public void No(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void instagram() {
        System.out.println("instagram");
    }

    @FXML
    public void github() {
        System.out.println("Github");
    }

    @FXML
    public void facebook() {
        System.out.println("facebook");
    }

    @FXML
    public void whatsapp() {
        System.out.println("whatsapp");
    }

    // ComboBox for Mentioning Gender
    ObservableList<String> list = FXCollections.observableArrayList(new String("Male"), new String("Female"),
            new String("Other"), new String("Robot"));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gender.setItems(list);
    }
}