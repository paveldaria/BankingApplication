package org.example.mazebank.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ViewFactory {

    private AnchorPane homeView;
    private AnchorPane transferView;
    private AnchorPane createClientView;
    private AnchorPane depositView;
    private AnchorPane accountsView;
    private final StringProperty SelectedMenuItem;
    private AccountType loginAccountType;


    public ViewFactory(){
        this.SelectedMenuItem = new SimpleStringProperty("");
        this.loginAccountType = AccountType.Client;
    }


    public StringProperty getSelectedMenuItem(){                                                                        // Getters and Setters
        return SelectedMenuItem;
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }



    // Login Window
    public void showLoginWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/FXML/Login/Login.fxml"));
        createStage(fxmlLoader);
    }


    // Client Window
    public void showClientWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/FXML/Client/Client.fxml"));
        createStage(fxmlLoader);
    }

    public AnchorPane getHomeView(){
            try{
                homeView = new FXMLLoader(ViewFactory.class.getResource("/FXML/Client/Home.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return homeView;
    }

    public AnchorPane getTransferView(){
            try{
                transferView = new FXMLLoader(ViewFactory.class.getResource("/FXML/Client/Transfer.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        return transferView;
    }

    public AnchorPane getAccountsView(){
            try{
                accountsView = new FXMLLoader(ViewFactory.class.getResource("/FXML/Client/Accounts.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        return accountsView;
    }


    // Admin Window
    public void showAdminWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/FXML/Admin/Admin.fxml"));
        createStage(fxmlLoader);
    }

    public AnchorPane getCreateClientView(){
            try{
                createClientView = new FXMLLoader(ViewFactory.class.getResource("/FXML/Admin/CreateClients.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        return createClientView;
    }

    public AnchorPane getDepositView(){
            try{
                depositView = new FXMLLoader(ViewFactory.class.getResource("/FXML/Admin/Deposit.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        return depositView;
    }


    // Create and Close stages
    private void createStage(FXMLLoader fxmlLoader){
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Maze Bank");
        stage.setResizable(false);
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();

    }

}
