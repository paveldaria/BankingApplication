package org.example.mazebank.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.mazebank.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {

    public Button dashboardButton;
    public Button accountsButton;
    public Button logoutButton;
    public Button sendMoneyButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }


    private void addListeners(){
        dashboardButton.setOnAction(actionEvent -> onHome());
        sendMoneyButton.setOnAction(actionEvent -> onTransfer());
        accountsButton.setOnAction(actionEvent -> onAccounts());
        logoutButton.setOnAction(actionEvent -> onLogout());

    }

    private void onHome(){
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("Home");
    }

    private void onTransfer(){
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("Transfer");
    }

    private void onAccounts(){
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("Accounts");
    }

    private void onLogout(){
        Stage stage = (Stage)logoutButton.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }

}
