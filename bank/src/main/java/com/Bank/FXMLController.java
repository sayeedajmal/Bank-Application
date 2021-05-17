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
    JFXTextField username = new JFXTextField();
    JFXTextField login_username = new JFXTextField();
    public JFXDatePicker birthdate = new JFXDatePicker();
    public JFXComboBox<String> gender = new JFXComboBox<>();
    JFXTextField email = new JFXTextField();
    JFXTextField phone = new JFXTextField();
    JFXPasswordField password = new JFXPasswordField();
    JFXPasswordField login_password = new JFXPasswordField();
    JFXTextField account = new JFXTextField();
    JFXTextField ifsc = new JFXTextField();

    /* THIS SECTION IS FOR GETTING CONNECTION FROM MYSQL SERVER */
    Connection connection = DBConnect.Embadded();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String username_String = username.getText();

    public FXMLController() throws SQLException, ClassNotFoundException {
        connection = DBConnect.Embadded();
    }

    /* REQUESTING FOCUS OF KEYBOARD AND MOUSE OVER TEXTFIELD */
    public void focus() {
        username.requestFocus();
        password.requestFocus();
        email.requestFocus();
        phone.requestFocus();
    }

    /* THIS SECTION IS FOR Access */
    @FXML
    public void Access(ActionEvent event) throws IOException, SQLException {
        // Convert USRENAME TO String and PASSWORD to String
        focus();
        String USERNAME = username.getText();
        String PASSWORD = password.getText();
        String authentic = "SELECT * FROM" + username_String.toUpperCase() + " WHERE USERNAME = ? and PASSWORD = ?";
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

        if (!doesTableExists(username_String.toUpperCase(), connection)) {
            String users_create = "CREATE TABLE " + username_String.toUpperCase()
                    + " (USERNAME varchar(20) NOT NULL, BIRTHDATE varchar(20) NOT NULL,GENDER varchar(8) NOT NULL,ACCOUNT integer(20) PRIMARY KEY,IFSC varchar(15) NOT NULL,EMAIL varchar(30) not null, PHONE integer(12) NOT NULL, PASSWORD varchar(10) NOT NULL,AMMOUNT decimal(20))";
            Statement statement = connection.createStatement();
            statement.execute(users_create);
            System.out.println("Created table USERS.");
        } else {
            System.out.println("User table Already Exists");
        }
        String USERNAME = username.getText();
        LocalDate date = birthdate.getValue();
        String BIRTHDATE = date.toString();
        String GENDER = gender.getValue();
        String ACCOUNT = account.getText();
        String IFSC = ifsc.getText();
        String EMAIL = email.getText();
        String PHONE = phone.getText();
        String PASSWORD = password.getText();

        String insert = "INSERT INTO" + username_String.toUpperCase()
                + "(USERNAME,BIRTHDATE,GENDER,ACCOUNT,IFSC,EMAIL,PHONE,PASSWORD) VALUES(?,?,?,?,?,?,?,?)";

        preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setString(1, USERNAME);
        preparedStatement.setString(2, BIRTHDATE);
        preparedStatement.setString(3, GENDER);
        preparedStatement.setString(4, ACCOUNT);
        preparedStatement.setString(5, IFSC);
        preparedStatement.setString(6, EMAIL);
        preparedStatement.setString(7, PHONE);
        preparedStatement.setString(8, PASSWORD);

        if (!USERNAME.isBlank() && !PASSWORD.isBlank() && !ACCOUNT.isBlank() && !IFSC.isBlank() && !PHONE.isBlank()
                && !EMAIL.isBlank() && !BIRTHDATE.isBlank() && !GENDER.isBlank()) {
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