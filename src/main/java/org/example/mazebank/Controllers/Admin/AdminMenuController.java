package org.example.mazebank.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.mazebank.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {

    public Button createClientsButton;
    public Button depositButton;
    public Button logoutAdminButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        depositButton.setOnAction(actionEvent -> onDeposit());
        createClientsButton.setOnAction(actionEvent -> onCreateClient());
        logoutAdminButton.setOnAction(actionEvent -> onLogout());
    }

    private void onDeposit(){
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("Deposit");
    }

    private void onCreateClient(){
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("CreateClient");
    }

    private void onLogout(){
        Stage stage = (Stage)logoutAdminButton.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }

}
