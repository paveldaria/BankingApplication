package org.example.mazebank.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.example.mazebank.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public BorderPane adminWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getSelectedMenuItem().addListener( (observableValue, oldVal, newVal) -> {
            if (newVal.equals("Deposit")) {
                adminWindow.setCenter(Model.getInstance().getViewFactory().getDepositView());
            } else {
                adminWindow.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
            }
        } );

    }

}
