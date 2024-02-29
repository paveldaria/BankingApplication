package org.example.mazebank.Controllers.Client;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.mazebank.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Text firstName;
    public Label checking_balance;
    public Label checking_number;
    public Label savings_balance;
    public Label savings_number;
    public TextField depositField;
    public Label depositMessageLabel;
    public Button depositButton;
    public TextField withdrawField;
    public Label withdrawMessageLabel;
    public Button withdrawButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        addListeners();
    }

    private void bindData(){
        firstName.textProperty().bind(Bindings.concat("Hi, ").concat(Model.getInstance().getClient().firstNameProperty()));
        checking_number.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().acountNumberProperty());
        checking_balance.textProperty().bind(Bindings.concat("$").concat(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString() ));
        savings_number.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().acountNumberProperty());
        savings_balance.textProperty().bind( Bindings.concat("$").concat(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString() ));
    }


    private void addListeners(){
        depositButton.setOnAction(actionEvent -> onDeposit());
        withdrawButton.setOnAction(actionEvent -> onWithdraw());
    }


    private void onDeposit(){
        if(!depositField.getText().isEmpty()) {
            withdrawMessageLabel.setText("");
            try{
                  double amount = Double.parseDouble(depositField.getText());
                  deposit(Model.getInstance().getClient().getUsernmame(),amount);
                  depositMessageLabel.setText("Funds added");
                  depositField.setText("");
              }  catch (NumberFormatException e){
                 depositMessageLabel.setText("Invalid number");
                 depositField.setText("");
              }
           }
        else depositMessageLabel.setText("Enter funds!");
    }


    private void onWithdraw(){
        if(!withdrawField.getText().isEmpty()) {
            depositMessageLabel.setText("");
            try{
                  double amount = Double.parseDouble(withdrawField.getText());
                  if (withdraw(Model.getInstance().getClient().getUsernmame(),amount)){
                      withdrawMessageLabel.setText("Funds withdrawn");
                      withdrawField.setText("");
                  } else {
                      withdrawMessageLabel.setText("Insufficient funds");
                      withdrawField.setText("");
                  }
              }  catch (NumberFormatException e){
               withdrawMessageLabel.setText("Invalid number");
               withdrawField.setText("");
                 }
           }
        else withdrawMessageLabel.setText("Enter funds!");
    }


    private void deposit(String username, double amount) {
        double balance = Model.getInstance().getClient().checkingAccountProperty().getValue().balanceProperty().get();
        System.out.println(balance);
        balance = balance + amount;
        Model.getInstance().getClient().checkingAccountProperty().getValue().balanceProperty().set(balance);
        Model.getInstance().getDatabaseDriver().updateBalanceInCA(username, balance);
    }

    private boolean withdraw(String username, double amount) {
        boolean flag = true;
        double balance = Model.getInstance().getClient().checkingAccountProperty().getValue().balanceProperty().get();
        if (balance>=amount){
            balance -= amount;
            Model.getInstance().getClient().checkingAccount.getValue().balanceProperty().set(balance);
            Model.getInstance().getDatabaseDriver().updateBalanceInCA(username, balance);
        } else flag = false;
        return flag;
    }

}
