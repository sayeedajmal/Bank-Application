package com.Bank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable {
    public Label time = new Label();
    public Label date = new Label();
    public AnchorPane dashpane = new AnchorPane();
    public PieChart expensepie = new PieChart();
    Stage window = new Stage();
    /* ACCOUNTS CONTROLLATION */
    public Label NAME = new Label();
    public Label NUMBER = new Label();
    public Label AMMOUNT = new Label();
    public Label IFSC = new Label();
    String uname;
    public JFXTextField depositeammount = new JFXTextField();

    /* THIS SECTION IS FOR PIE PieChart */
    ObservableList<PieChart.Data> Data = FXCollections.observableArrayList(new PieChart.Data("Clothes", 16.66),
            new PieChart.Data("Food", 16.66), new PieChart.Data("Others", 16.66), new PieChart.Data("Education", 16.67),
            new PieChart.Data("Healths", 16.66), new PieChart.Data("Entertainments", 16.66),
            new PieChart.Data("Bills", 16.66));

    @FXML /* Contact Me! */
    public void Contact() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Contact.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Image icon = new Image("/Images/email.png");
        stage.setTitle("Contact");
        stage.centerOnScreen();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /* TRANSFER MONEY METHOD */
    public void Transfer() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Transfer.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Image icon = new Image("/Images/enter.png");
        stage.setTitle("Transfer");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /* Banking */
    public void Banking() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Banking.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Image Icon = new Image("/Images/user.png");
        stage.getIcons().add(Icon);
        stage.setTitle("Banking");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void depositebutton() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Deposite.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Image Icon = new Image("/Images/user.png");
        stage.getIcons().add(Icon);
        stage.setTitle("Banking");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void Donebutton() {

        try {
            Connection connection = DBConnect.Embadded();
            Statement statement = connection.createStatement();
            String fetch = "update " + uname.toUpperCase() + " set ammount='33' WHERE USERNAME='" + uname.toLowerCase()
                    + " '";
            statement.execute(fetch);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                NAME.setText(resultSet.getString("username"));
                NUMBER.setText(resultSet.getString("account"));
                AMMOUNT.setText(resultSet.getString("ammount"));
                IFSC.setText(resultSet.getString("ifsc"));
                depositeammount.setText(null);
                System.out.println("Deposited hehehehe");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("You Clicked the Done button");
    }

    @FXML
    public void minimize(ActionEvent event) {

    }

    @FXML
    public void No(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    /* THIS IS THE TABLE VIEW FOR MiniTransactions */
    @FXML
    TableView<MiniTransaction> tableview = new TableView<>();
    public TableColumn<MiniTransaction, Integer> Today = new TableColumn<>();
    public TableColumn<MiniTransaction, String> Where = new TableColumn<>();
    public TableColumn<MiniTransaction, String> Payment = new TableColumn<>();
    public TableColumn<MiniTransaction, Integer> Ammount = new TableColumn<>();
    /* SHOWING NAME ACCOUNT NUMBER & AMMOUNT */

    @Override
    /* <<=============== This SECTION is for Initialize =================>> */
    public void initialize(URL location, ResourceBundle resources) {
        /* <<=============== Getting Time And Date =================>> */
        LocalTime ltime = LocalTime.now();
        DateTimeFormatter lTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String LTime = ltime.format(lTimeFormatter);
        time.setText(LTime);
        LocalDate ldate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String lDate = ldate.format(formatter);
        date.setText(lDate);
        /* <<=============== ----------------------- =================>> */
        /* ObservableList for Adding Informations */
        ObservableList<MiniTransaction> list = FXCollections.observableArrayList(
                new MiniTransaction(lDate, "Lucky Medical", "Google Pay", 345 + "$"),
                new MiniTransaction(lDate, "Ticket", "Paytm", 500 + "$"),
                new MiniTransaction(lDate, "AirTicket", "Dabit Card", 200 + "$"),
                new MiniTransaction(lDate, "Mouse", "Dabit Card", 35 + "$"),
                new MiniTransaction(lDate, "Recharge", "Google Pay", 90 + "$"),
                new MiniTransaction(lDate, "Phone Cover", "Google Pay", 05 + "$"),
                new MiniTransaction(lDate, "Sweet", "Google Pay", 23 + "$"),
                new MiniTransaction(lDate, "Speacker", "Credit card", 250 + "$"));
        Today.setCellValueFactory(new PropertyValueFactory<MiniTransaction, Integer>("Today"));
        Where.setCellValueFactory(new PropertyValueFactory<MiniTransaction, String>("Where"));
        Payment.setCellValueFactory(new PropertyValueFactory<MiniTransaction, String>("Payment"));
        Ammount.setCellValueFactory(new PropertyValueFactory<MiniTransaction, Integer>("Ammount"));
        tableview.setItems(list);
        /* <<=============== Getting Time And Date =================>> */
        expensepie.setData(Data);
        /* FETCHING DATA FROM THE DATABASE */
        File file = new File("username.txt");
        try {
            Connection connection = DBConnect.Embadded();
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                uname = input.nextLine();
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

}
