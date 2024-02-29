package org.example.mazebank.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.mazebank.Models.Model;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class DepositController implements Initializable {

    public TextField username_search;
    public Button search_button;
    public TextField deposit_funds_admin;
    public Button deposit_button;
    public Label message_label_search;
    public Label message_label_deposit;
    private boolean usernameFound;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }


    private void addListeners() {
        search_button.setOnAction(actionEvent -> onSearch());
        deposit_button.setOnAction(actionEvent -> onDeposit());
    }


    private void onSearch() {
        String username = username_search.getText();
        if (!username.isEmpty()) {
            searchByUsername(username);
            if (usernameFound) {
                message_label_search.setText("Client found");
                message_label_deposit.setText("");
            } else {
                message_label_search.setText("Client not found");
                username_search.setText("");
            }
        } else
            message_label_search.setText("Enter username");
    }


    private void onDeposit() {
        if (usernameFound) {
            String username = username_search.getText();
            String funds = deposit_funds_admin.getText();
            if (!funds.isEmpty()) {
                try {
                    double amount = Double.parseDouble(funds);
                    updateBalance(username, amount);
                    message_label_deposit.setText("The funds have been added succesfully");
                    username_search.setText("");
                    message_label_search.setText("");
                    deposit_funds_admin.setText("");

                } catch (NumberFormatException e) {
                    message_label_deposit.setText("Invalid number");
                    deposit_funds_admin.setText("");
                }
            } else message_label_deposit.setText("Enter funds");
        } else message_label_deposit.setText("Search for a client first!");
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


    private void updateBalance(String username, double amount) {
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getBalanceFromCA(username);
        try {
            double balance = resultSet.getDouble(1);
            balance += amount;
            Model.getInstance().getDatabaseDriver().updateBalanceInCA(username, balance);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}