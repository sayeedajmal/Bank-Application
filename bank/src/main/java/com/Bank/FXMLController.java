package com.Bank;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLController implements Initializable {
    public AnchorPane dashpane;
    public JFXTextField username;
    public JFXDatePicker birthdate = new JFXDatePicker();
    public JFXComboBox<String> gender = new JFXComboBox<>();
    public JFXTextField email;
    public JFXTextField phone;
    public JFXPasswordField password;

    /* THIS SECTION IS FOR GETTING CONNECTION FROM MYSQL SERVER */
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public FXMLController() throws SQLException, ClassNotFoundException {
        connection = DBConnect.Embadded();
    }

    /* REQUESTING FOCUS OF KEYBOARD AND MOUSE OVER TEXTFIELD */
    public void focus() {
        username.requestFocus();
        password.requestFocus();
        /*
         * email.requestFocus(); phone.requestFocus();
         */
    }

    /* THIS SECTION IS FOR Access */
    @FXML
    public void Access(ActionEvent event) throws IOException {
        // Convert USRENAME TO String and PASSWORD to String
        focus();
        String USERNAME = username.getText();
        String PASSWORD = password.getText();
        String authentic = "SELECT * FROM users WHERE USERNAME = ? and PASSWORD = ?";
        try {
            preparedStatement = connection.prepareStatement(authentic);
            preparedStatement.setString(1, USERNAME);
            preparedStatement.setString(2, PASSWORD);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                /*
                 * JFXDialogLayout layout = new JFXDialogLayout(); layout.setBody(new
                 * Label("We are Fixing it.... SO, Wait Untill I fix it.")); JFXAlert<Void>
                 * alert = new JFXAlert<>();
                 * alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                 * alert.setContent(layout); alert.initModality(Modality.APPLICATION_MODAL);
                 * alert.showAndWait();
                 */

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
                System.out.println("Oooops! Username or password is invalid!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    /* THIS SECTION OS FOR REGISTERING NEW USERS..... */
    @FXML
    public void register(ActionEvent event) throws IOException, SQLException {

        if (!doesTableExists("users", connection)) {
            String users = "CREATE TABLE users (USERNAME VARCHAR(20) PRIMARY KEY, BIRTHDATE VARCHAR(20) NOT NULL, GENDER VARCHAR(8) NOT NULL, EMAIL VARCHAR(30) NOT NULL, PHONE VARCHAR(12) NOT NULL, PASSWORD VARCHAR(10) NOT NULL)";
            Statement statement = connection.createStatement();
            statement.execute(users);
            System.out.println("Created table USERS.");
        } else {
            System.out.println("User table Already Exists");
        }
        String USERNAME = username.getText();
        String PASSWORD = password.getText();
        String PHONE = phone.getText();
        String EMAIL = email.getText();
        LocalDate date = birthdate.getValue();
        String BIRTHDATE = date.toString();
        String GENDER = gender.getValue();

        String insert = "INSERT INTO users(USERNAME,PASSWORD,PHONE,EMAIL,BIRTHDATE,GENDER) VALUES(?,?,?,?,?,?)";

        preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setString(1, USERNAME);
        preparedStatement.setString(2, PASSWORD);
        preparedStatement.setString(3, PHONE);
        preparedStatement.setString(4, EMAIL);
        preparedStatement.setString(5, BIRTHDATE);
        preparedStatement.setString(6, GENDER);

        if (!USERNAME.isBlank() && !PASSWORD.isBlank() && !PHONE.isBlank() && !EMAIL.isBlank() && !BIRTHDATE.isBlank()
                && !GENDER.isBlank()) {
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
            System.out.println("Fill All Fields");
        }
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