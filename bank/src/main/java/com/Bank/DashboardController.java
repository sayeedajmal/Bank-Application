package com.Bank;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.jfoenix.controls.JFXTextField;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

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
    public void depositebutton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Deposite.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Image Icon = new Image("/Images/user.png");
        stage.getIcons().add(Icon);
        stage.setTitle("Deposite");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void withdrawbutton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/withdrawal.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Image Icon = new Image("/Images/user.png");
        stage.getIcons().add(Icon);
        stage.setTitle("WithDraw");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void Setting(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CloseAccount.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Image Icon = new Image("/Images/user.png");
        stage.getIcons().add(Icon);
        stage.setTitle("Settings");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void minimize(ActionEvent event) {

    }

    @FXML
    public void No(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void close(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        String user_path = System.getProperty("user.home") + File.separator + ".config";
        user_path += File.separator + "username";
        File user = new File(user_path + ".txt");
        String pass_path = System.getProperty("user.home") + File.separator + ".config";
        pass_path += File.separator + "password";
        File Pass = new File(pass_path + ".txt");
        if (user.exists() && Pass.exists()) {
            user.delete();
            Pass.delete();
        }
    }

    /* THIS IS THE TABLE VIEW FOR MiniTransactions */
    @FXML
    TableView<MiniTransaction> tableview = new TableView<>();
    public TableColumn<MiniTransaction, Integer> Today = new TableColumn<>();
    public TableColumn<MiniTransaction, String> Where = new TableColumn<>();
    public TableColumn<MiniTransaction, String> Payment = new TableColumn<>();
    public TableColumn<MiniTransaction, Integer> Ammount = new TableColumn<>();
    /* SHOWING NAME ACCOUNT NUMBER & AMMOUNT */

    /* Refresh Button */
    public void refresh(ActionEvent event) throws InterruptedException, IOException {
        No(event);
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
    }

    @Override
    /* <<=============== This SECTION is for Initialize =================>> */
    public void initialize(URL location, ResourceBundle resources) {

        /* <<=============== Getting Time And Date =================>> */
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            time.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        LocalDate currentDate = LocalDate.now();
        Timeline Day = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            String lDate = currentDate.getDayOfMonth() + ":" + currentDate.getMonthValue() + ":"
                    + currentDate.getYear();
            date.setText(lDate);
        }), new KeyFrame(Duration.seconds(1)));
        Day.setCycleCount(Animation.INDEFINITE);
        Day.play();
        String lDate = currentDate.toString();

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

        /* ====================================================================== */
        /* FETCHING DATA FROM THE DATABASE */
        String user_path = System.getProperty("user.home") + File.separator + ".config";
        user_path += File.separator + "username";
        File file = new File(user_path + ".txt");
        Timeline FetchingAmmount = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                Connection connection = DBConnect.Embadded();
                Scanner input = new Scanner(file);
                while (input.hasNext()) {
                    uname = input.next();
                    String query = "select * from " + uname.toUpperCase();
                    Statement statement = connection.createStatement();
                    statement.execute(query);

                    try {
                        ResultSet resultSet = statement.getResultSet();
                        if (resultSet.next()) {
                            NAME.setText(resultSet.getString("username"));
                            NUMBER.setText(resultSet.getString("account"));
                            AMMOUNT.setText(resultSet.getString("ammount"));
                            IFSC.setText(resultSet.getString("ifsc"));
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                input.close();
            } catch (Exception e1) {
                System.out.println(e1.getLocalizedMessage());
            }
        }), new KeyFrame(Duration.seconds(1)));
        FetchingAmmount.setCycleCount(Animation.INDEFINITE);
        FetchingAmmount.play();

        /* =========================================================== */
    }

}
