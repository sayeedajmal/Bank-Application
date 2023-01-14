package com.Bank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
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
    public JFXTextField TransferAmmount = new JFXTextField();
    public Label information = new Label();
    String uname;
    String Account;

    @FXML
    public void Ready(ActionEvent event) throws IOException {
        /* getting Username from user to fetch the data of database */
        String String_name = temp_name.getText();
        String String_accountNumber = temp_number.getText();
        this.uname = String_name;
        this.Account = String_accountNumber;

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
                            // Creating Temp File of Second Party Information
                            // Username
                            String temp_username = temp_name.getText();
                            String user_path = System.getProperty("user.home") + File.separator + ".config";
                            user_path += File.separator + "temp_username";
                            File user = new File(user_path + ".txt");
                            FileWriter fileWriter = new FileWriter(user);
                            fileWriter.append(temp_username.toUpperCase());
                            fileWriter.close();

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

    public void DoneTransfer(ActionEvent event) throws SQLException, FileNotFoundException, InterruptedException {
        System.out.println("You Clicked DoneTranser Button");

        // Getting Information of First Party
        String user_path = System.getProperty("user.home") + File.separator + ".config";
        user_path += File.separator + "username";
        File file = new File(user_path + ".txt");
        Connection connection = DBConnect.Embadded();
        Scanner input = new Scanner(file);

        while (input.hasNext()) {
            this.uname = input.next();
            String query = "select * from " + uname.toUpperCase();
            Statement statement = connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                // Getting the balace from The First Party
                int Balance = resultSet.getInt("ammount");
                if (Balance > 0) {

                    // Getting the ammount from First Party and Add the Ammount to the Second Party
                    String transfer = TransferAmmount.getText();
                    int TransferBalance = Integer.parseInt(transfer);

                    // Subtracting the ammount in first party
                    Balance -= TransferBalance;

                    // Updating the Ammount of FIRST PARTY========
                    String Account = resultSet.getString("account");
                    String Query = "UPDATE " + uname.toUpperCase() + " SET AMMOUNT=" + Balance + " WHERE ACCOUNT ="
                            + Account;
                    PreparedStatement preparedStatement = connection.prepareStatement(Query);
                    preparedStatement.executeUpdate(Query);
                    /* ========================================================= */

                    // ===== Updating the Information of SECOND PARTY
                    String temp_user_path = System.getProperty("user.home") + File.separator + ".config";
                    temp_user_path += File.separator + "temp_username";
                    file = new File(temp_user_path + ".txt");
                    input = new Scanner(file);
                    // Transfering the Ammount to the Second Party
                    while (input.hasNext()) {
                        String temp_username = input.next();
                        query = "select * from " + temp_username.toUpperCase();
                        statement.executeQuery(query);
                        resultSet = statement.getResultSet();
                        if (resultSet.next()) {
                            String temp_Account = resultSet.getString("account");
                            Balance = resultSet.getInt("ammount");

                            // Adding Balance to The Second Party
                            Balance += TransferBalance;

                            // Updating the Ammount of SECOND PARTY========
                            Query = "UPDATE " + temp_username.toUpperCase() + " SET AMMOUNT=" + Balance
                                    + " WHERE ACCOUNT =" + temp_Account;
                            preparedStatement = connection.prepareStatement(Query);
                            preparedStatement.executeUpdate(Query);

                            TransferAmmount.setText(null);

                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Transfer Message");
                            alert.setContentText("TransFer Done! 😍");
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.showAndWait();
                            Thread.sleep(500);
                            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                        }
                    }
                }

            } else {
                System.out.println("InSufficient Balance: Haha");
                TransferAmmount.setText(null);
                return;
            }
        }
        input.close();
    }

    // Fetching Information and Initialize it
    public void FetchInformation() {
        String user_path = System.getProperty("user.home") + File.separator + ".config";
        user_path += File.separator + "username";
        File file = new File(user_path + ".txt");
        try {
            Connection connection = DBConnect.Embadded();
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                this.uname = input.next();
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

    @FXML
    public void No(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FetchInformation();
    }

}
