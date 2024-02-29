package org.example.mazebank.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.mazebank.Models.Model;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class TransferController implements Initializable {

    public TextField usernameField;
    public TextField amountField;
    public Button searchButton;
    public Label messageLabel_search;
    public Button transferButton;
    public Label messageLabel_transfer;
    private boolean usernameFound = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchButton.setOnAction(actionEvent -> onSearch());
        transferButton.setOnAction(actionEvent -> onTransfer());
    }

    private void onSearch() {
        if (!usernameField.getText().isEmpty()) {
            String username = usernameField.getText();
            searchByUsername(username);
            if (usernameFound) {
                messageLabel_search.setText("Client found");
                messageLabel_transfer.setText("");
            } else {
                messageLabel_search.setText("Client not found");
                usernameField.setText("");
            }
        } else
            messageLabel_search.setText("Enter username");
    }


    private void onTransfer() {
        if (usernameFound) {
            String payeeUsername = usernameField.getText();
            String senderUsername = Model.getInstance().getClient().getUsernmame();
            if (!amountField.getText().isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if(withdraw(senderUsername,amount)) {
                        deposit(payeeUsername, amount);
                        messageLabel_transfer.setText("The funds have been added succesfully");
                        messageLabel_search.setText("");
                        usernameField.setText("");
                        amountField.setText("");
                    } else
                        messageLabel_transfer.setText("Insuffiecient funds");
                } catch (NumberFormatException e) {
                    messageLabel_transfer.setText("Invalid number");
                    amountField.setText("");
                }
            } else messageLabel_transfer.setText("Enter funds");
        } else messageLabel_transfer.setText("Search for a client first!");
    }


    private void searchByUsername(String username) {
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getUsername(username);
        try {
            if (resultSet.isBeforeFirst())
                this.usernameFound = true;
            else this.usernameFound = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deposit(String username, double amount){
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getBalanceFromCA(username);
        try {
            if(resultSet.isBeforeFirst()){
                double balance = Double.parseDouble(resultSet.getString(1));
                balance += amount;
                Model.getInstance().getDatabaseDriver().updateBalanceInCA(username,balance);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private boolean withdraw(String username, double amount) {
        boolean flag = true;
        double balance = Model.getInstance().getClient().checkingAccountProperty().getValue().balanceProperty().get();
        if (balance>=amount){
            balance -= amount;
            Model.getInstance().getClient().checkingAccountProperty().getValue().balanceProperty().set(balance);
            Model.getInstance().getDatabaseDriver().updateBalanceInCA(username,balance);
        } else flag = false;
        return flag;
    }

}
