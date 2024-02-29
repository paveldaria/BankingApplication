package org.example.mazebank.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.mazebank.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;


public class AccountsController implements Initializable {

    public Text number_checkingAcc;
    public Text balance_checkingAcc;
    public Text number_savingsAcc;
    public Text balance_savingsAcc;
    public TextField amount_checkingAcc;
    public Button transferButton_checkingAcc;
    public TextField amount_savingsAcc;
    public Button transferButton_savingsAcc;
    public Label messageLabel_savingsAcc;
    public Label messageLabel_checkingAcc;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        addListeners();
    }


    private void bindData(){
        number_checkingAcc.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().acountNumberProperty());
        balance_checkingAcc.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString());
        number_savingsAcc.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().acountNumberProperty());
        balance_savingsAcc.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString());
    }


    private void addListeners(){
        transferButton_checkingAcc.setOnAction(actionEvent -> onTransferCA());
        transferButton_savingsAcc.setOnAction(actionEvent -> onTransferSA());
    }


    private void onTransferCA(){
        if(!amount_checkingAcc.getText().isEmpty()){
            try{
                String username = Model.getInstance().getClient().getUsernmame();
                double amount = Double.parseDouble(amount_checkingAcc.getText());
                if(withdrawFromCA(username, amount)) {
                    depositInSA(username, amount);
                    messageLabel_checkingAcc.setText("Funds transferred successfully");
                    amount_checkingAcc.setText("");
                } else {
                    messageLabel_checkingAcc.setText("Insufficient funds");
                    amount_checkingAcc.setText("");
                }
            } catch (NumberFormatException e){
                messageLabel_checkingAcc.setText("Invalid Number");
                amount_checkingAcc.setText("");

            }
        } else messageLabel_checkingAcc.setText("Enter funds!");
    }


    private void onTransferSA(){
        if(!amount_savingsAcc.getText().isEmpty()){
            try{
                String username = Model.getInstance().getClient().getUsernmame();
                double amount = Double.parseDouble(amount_savingsAcc.getText());
                if(withdrawFromSA(username, amount)) {
                    depositInCA(username, amount);
                    messageLabel_savingsAcc.setText("Funds transferred successfully");
                    amount_savingsAcc.setText("");
                } else {
                    messageLabel_savingsAcc.setText("Insufficient funds");
                    amount_savingsAcc.setText("");
                }
            } catch (NumberFormatException e){
                messageLabel_savingsAcc.setText("Invalid Number");
                amount_savingsAcc.setText("");

            }
        } else messageLabel_savingsAcc.setText("Enter funds!");
    }


    private boolean withdrawFromCA(String username, double amount) {
        boolean flag = true;
        double balance = Model.getInstance().getClient().checkingAccountProperty().getValue().balanceProperty().get();
        if (balance>=amount){
            balance -= amount;
            Model.getInstance().getClient().checkingAccountProperty().getValue().balanceProperty().set(balance);
            Model.getInstance().getDatabaseDriver().updateBalanceInCA(username,balance);
        } else flag = false;
        return flag;
    }

    private boolean withdrawFromSA(String username, double amount) {
        boolean flag = true;
        double balance = Model.getInstance().getClient().savingsAccountProperty().getValue().balanceProperty().get();
        if (balance>=amount){
            balance -= amount;
            Model.getInstance().getClient().savingsAccountProperty().getValue().balanceProperty().set(balance);
            Model.getInstance().getDatabaseDriver().updateBalanceInSA(username,balance);
        } else flag = false;
        return flag;
    }

    private void depositInCA(String username, double amount){
        double balance = Model.getInstance().getClient().checkingAccountProperty().getValue().balanceProperty().get();
        balance += amount;
        Model.getInstance().getClient().checkingAccountProperty().getValue().balanceProperty().set(balance);
        Model.getInstance().getDatabaseDriver().updateBalanceInSA(username,balance);
    }

    private void depositInSA(String username, double amount){
        double balance = Model.getInstance().getClient().savingsAccountProperty().getValue().balanceProperty().get();
        balance += amount;
        Model.getInstance().getClient().savingsAccountProperty().getValue().balanceProperty().set(balance);
        Model.getInstance().getDatabaseDriver().updateBalanceInSA(username,balance);
    }

}



