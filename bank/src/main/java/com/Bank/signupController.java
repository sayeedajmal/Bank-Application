package com.Bank;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class signupController implements Initializable {
    public Label label = new Label();
    public JFXTextField username = new JFXTextField();
    public JFXComboBox<String> gender = new JFXComboBox<>();
    public DatePicker birthdate = new DatePicker();
    public JFXTextField account = new JFXTextField();
    public JFXTextField ifsc = new JFXTextField();
    public JFXTextField email = new JFXTextField();
    public JFXTextField phone = new JFXTextField();
    public JFXPasswordField password = new JFXPasswordField();

    public void close(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void signup(ActionEvent event) throws SQLException, IOException {
        Connection connection = DBConnect.Embadded();
        String USERNAME = username.getText();
        LocalDate date = birthdate.getValue();
        String BIRTHDATE = date.toString();
        String GENDER = gender.getValue();
        String ACCOUNT = account.getText();
        String IFSC = ifsc.getText();
        String EMAIL = email.getText();
        String PHONE = phone.getText();
        String PASSWORD = password.getText();

        try {
            if (!USERNAME.isBlank() && !PASSWORD.isBlank() && !ACCOUNT.isBlank() && !IFSC.isBlank() && !PHONE.isBlank()
                    && !EMAIL.isBlank() && !BIRTHDATE.isBlank() && !GENDER.isBlank() && !BIRTHDATE.isBlank()) {
                String users_create = "create table " + username.getText().toUpperCase() + " ( "
                        + "username varchar(20) not null, birthdate varchar(20) not null,gender varchar(8) not null,account varchar(20) not null,ifsc varchar(15),email varchar(30), phone varchar(20) not null, password varchar(10),ammount integer ,primary key(account)"
                        + " )";
                Statement statement = connection.createStatement();
                statement.execute(users_create);
                String insert = "insert into " + username.getText().toUpperCase() + " values (?,?,?,?,?,?,?,?,500)";
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
                preparedStatement.setString(1, USERNAME);
                preparedStatement.setString(2, BIRTHDATE);
                preparedStatement.setString(3, GENDER);
                preparedStatement.setString(4, ACCOUNT);
                preparedStatement.setString(5, IFSC);
                preparedStatement.setString(6, EMAIL);
                preparedStatement.setString(7, PHONE);
                preparedStatement.setString(8, PASSWORD);
                preparedStatement.executeUpdate();

                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                scene.getStylesheets().add("/styles/Styles.css");
                stage.initStyle(StageStyle.TRANSPARENT);
                Image icon = new Image("/Images/enter.png");
                stage.setTitle("Login");
                stage.centerOnScreen();
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            } else {
                label.setText("Fill All Fields");
            }
        } catch (SQLException e) {
            label.setText(e.getLocalizedMessage());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList(new String("Male"), new String("Female"),
                new String("Other"), new String("Robot"));

        gender.setItems(list);
    }

}
