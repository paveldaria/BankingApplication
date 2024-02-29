package org.example.mazebank.Controllers.Login;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.mazebank.Models.Account;
import org.example.mazebank.Models.Model;
import org.example.mazebank.Views.AccountType;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    public AnchorPane mainWindow;
    public VBox leftWindow;
    public VBox loginWindow;
    public Button loginButton;
    public ChoiceBox <AccountType> choiceBox;
    public TextField usernameField;
    public PasswordField passwordField;
    public Label errorLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.setItems(FXCollections.observableArrayList(AccountType.Client,AccountType.Admin));
        choiceBox.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        choiceBox.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(choiceBox.getValue()));
        loginButton.setOnAction(actionEvent -> onLogin());
    }

    private void onLogin() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        if (Model.getInstance().getViewFactory().getLoginAccountType()==AccountType.Client){
              if(evaluateClientLogin(usernameField.getText(),passwordField.getText())) {
                  Model.getInstance().getViewFactory().showClientWindow();
                  Model.getInstance().getViewFactory().closeStage(stage);
              }
              else{
                  usernameField.setText("");
                  passwordField.setText("");
                  errorLabel.setText("invalid username or password");
              }
        }
        else {
             if(evaluateAdminLogin(usernameField.getText(),passwordField.getText())){
                 Model.getInstance().getViewFactory().showAdminWindow();
                 Model.getInstance().getViewFactory().closeStage(stage);
             }
             else{
                 usernameField.setText("");
                 passwordField.setText("");
                 errorLabel.setText("invalid username or password");
             }
        }
    }

    // Client Section
    public boolean evaluateClientLogin(String username, String password){
        boolean loginSuccess = false;
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getClientData(username,password);
        try{
            if(resultSet.isBeforeFirst()){
                Model.getInstance().getClient().firstNameProperty().set(resultSet.getString("FirstName"));
                Model.getInstance().getClient().lastNameProperty().set(resultSet.getString("LastName"));
                Model.getInstance().getClient().usernmameProperty().set(resultSet.getString("PayeeAddress"));
                Model.getInstance().getClient().checkingAccountProperty().set(getCheckingAccount(username));
                Model.getInstance().getClient().savingsAccountProperty().set(getSavingsAccount(username));
                loginSuccess = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return loginSuccess;
    }


    private Account getCheckingAccount(String username){
        Account checkingAccount = new Account("","",0);
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getCheckingAccount(username);
        try {
            if(resultSet.isBeforeFirst()){
                checkingAccount.ownerProperty().set(resultSet.getString("Owner"));
                checkingAccount.acountNumberProperty().set(resultSet.getString("AccountNumber"));
                checkingAccount.balanceProperty().set(resultSet.getDouble("Balance"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return checkingAccount;
    }


    private Account getSavingsAccount(String username){
        Account savingsAccount = new Account("","",0);
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getSavingsAccount(username);
        try {
            if(resultSet.isBeforeFirst()){
                savingsAccount.ownerProperty().set(resultSet.getString("Owner"));
                savingsAccount.acountNumberProperty().set(resultSet.getString("AccountNumber"));
                savingsAccount.balanceProperty().set(resultSet.getDouble("Balance"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return savingsAccount;
    }


    // Admin Section
    public boolean evaluateAdminLogin(String username, String password){
        boolean loginSuccess = false;
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getAdminData(username,password);
        try {
            if(resultSet.isBeforeFirst()){
                loginSuccess = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return loginSuccess;
    }


}
