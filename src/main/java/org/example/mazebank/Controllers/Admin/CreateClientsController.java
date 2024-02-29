package org.example.mazebank.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.mazebank.Models.Model;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class CreateClientsController implements Initializable {

    public TextField firstName_ca;
    public TextField lastName_ca;
    public TextField username_ca;
    public PasswordField password_ca;
    public TextField checkingAcc_amount;
    public TextField savingAcc_amount;
    public Button createAccountButton;
    public Label messageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createAccountButton.setOnAction(actionEvent -> createAccount());
    }

    private void createAccount(){
        if (allFieldsCompleted()) createClient();
        else messageLabel.setText("All fields must be completed");
    }

    private void createClient(){
        String firsName = firstName_ca.getText();
        String lastName = lastName_ca.getText();
        String username = username_ca.getText();
        String password = password_ca.getText();
        Model.getInstance().getDatabaseDriver().createClient(firsName,lastName,username,password);
        createCheckingAccount(username);
        createSavingsAccount(username);
        emptyFields();
        messageLabel.setText("Account created successfully");
    }

    private void createCheckingAccount(String username){
        double balance = Double.parseDouble(checkingAcc_amount.getText());
        String accountNumber = generateAccountNumber();
        Model.getInstance().getDatabaseDriver().createCheckingAccount(username,accountNumber,balance);
    }

    private void createSavingsAccount(String username){
        double balance = Double.parseDouble(savingAcc_amount.getText());
        String accountNumber = generateAccountNumber();
        Model.getInstance().getDatabaseDriver().createSavingsAccount(username,accountNumber,balance);
    }

    private String generateAccountNumber(){
        String firstSection = Integer.toString((new Random()).nextInt(9000)+1000);
        String secondSection = Integer.toString((new Random()).nextInt(9000)+1000);
        String thirdSection = Integer.toString((new Random()).nextInt(9000)+1000);
        String forthSection = Integer.toString((new Random()).nextInt(9000)+1000);
        return firstSection+" "+secondSection+" "+thirdSection+" "+forthSection;
    }

    private void emptyFields(){
        firstName_ca.setText("");
        lastName_ca.setText("");
        username_ca.setText("");
        password_ca.setText("");
        checkingAcc_amount.setText("");
        savingAcc_amount.setText("");
    }

    private boolean allFieldsCompleted(){
        boolean flag = true;
        if(firstName_ca.getText().isEmpty()) flag = false;
        else if(lastName_ca.getText().isEmpty()) flag = false;
             else if (username_ca.getText().isEmpty()) flag = false;
                  else if (password_ca.getText().isEmpty()) flag = false;
                       else if (checkingAcc_amount.getText().isEmpty()) flag = false;
                            else if (savingAcc_amount.getText().isEmpty()) flag = false;
        return flag;
    }

}
