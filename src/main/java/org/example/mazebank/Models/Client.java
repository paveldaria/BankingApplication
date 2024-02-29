package org.example.mazebank.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Client {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty usernmame;
    public final ObjectProperty<Account> checkingAccount;
    private final ObjectProperty<Account> savingsAccount;


    public Client(String firstName, String lastName, String username, Account checkingAccount, Account savingsAccount){
        this.firstName = new SimpleStringProperty(this,"FirstName",firstName);
        this.lastName = new SimpleStringProperty(this,"LastName",lastName);
        this.usernmame = new SimpleStringProperty(this,"Username",username);
        this.checkingAccount = new SimpleObjectProperty<>(this,"CheckingAccount",checkingAccount);
        this.savingsAccount = new SimpleObjectProperty<>(this,"SavingsAccount", savingsAccount);
    }


    public String getUsernmame() {
        return usernmame.get();
    }

    public StringProperty firstNameProperty() {return firstName;}

    public StringProperty lastNameProperty() {return lastName;}

    public StringProperty usernmameProperty() {return usernmame;}

    public ObjectProperty<Account> checkingAccountProperty() {return checkingAccount;}

    public ObjectProperty<Account> savingsAccountProperty() {return savingsAccount;}

}
