package org.example.mazebank.Models;

import java.sql.*;


public class DatabaseDriver {

    private Connection connection;

    public DatabaseDriver(){
        try{
            this.connection = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        } catch (SQLException e){
            e.printStackTrace();
        }

    }


    // Methods for retrieving, inserting and updating data in the database

    public ResultSet getClientData(String username, String password){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress='"+username+"' AND Password='"+password+"' ;" );
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }


    public ResultSet getAdminData(String username, String password){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Admins WHERE Username='"+username+"' AND Password='"+password+"' ;" );
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }


    public void createClient(String firstName, String lastName, String username, String password){
        Statement statement;
        try {
            statement=this.connection.createStatement();
            statement.executeUpdate("INSERT INTO " +
                                    "Clients (FirstName, LastName, PayeeAddress, Password) " +
                                    "VALUES ('"+firstName+"', '"+lastName+"', '"+username+"', '"+password+"');" );
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createCheckingAccount(String owner, String number, double balance){
        Statement statement;
        try{
            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO "+
                                    "CheckingAccounts (Owner, AccountNumber, Balance) " +
                                    "VALUES ('"+owner+"', '"+number+"', "+balance+") ") ;        // stringurile le includem intre ' '
        } catch (SQLException e){
            e.printStackTrace();
        }
     }

    public void createSavingsAccount(String owner, String number, double balance){
        Statement statement;
        try{
            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO "+
                    "SavingsAccounts (Owner, AccountNumber, Balance) " +
                    "VALUES ('"+owner+"', '"+number+"', "+balance+") ");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet getUsername(String username){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress='"+username+"' ;");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getCheckingAccount(String username){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE Owner='"+username+"' ;");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getSavingsAccount(String username){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='"+username+"' ;");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }


    public ResultSet getBalanceFromCA(String username){
        Statement statement;
        ResultSet resultSet = null;
        try{
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT Balance FROM CheckingAccounts WHERE Owner='"+username+"' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    public void updateBalanceInCA(String username, double amount){
        Statement statement;
        try{
            statement = this.connection.createStatement();
            statement.executeUpdate("UPDATE CheckingAccounts SET Balance="+amount+" WHERE Owner='"+username+"' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateBalanceInSA(String username, double amount){
        Statement statement;
        try{
            statement = this.connection.createStatement();
            statement.executeUpdate("UPDATE SavingsAccounts SET Balance="+amount+" WHERE Owner='"+username+"' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
