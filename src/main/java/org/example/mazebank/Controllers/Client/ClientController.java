package org.example.mazebank.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.example.mazebank.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    public BorderPane clientWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Model.getInstance().getViewFactory().getSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
               switch (newVal) {
                      case "Transfer" -> clientWindow.setCenter(Model.getInstance().getViewFactory().getTransferView());
                      case "Accounts" -> clientWindow.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                      default -> clientWindow.setCenter(Model.getInstance().getViewFactory().getHomeView());
              }
        } );

    }


}
