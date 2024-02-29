package org.example.mazebank.Models;

import org.example.mazebank.Views.ViewFactory;

public class Model {

    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private final Client client;


    private Model(){
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        this.client = new Client("","","",null,null);
    }


    public static synchronized Model getInstance(){
        if(model==null){
            model = new Model();
        }
        return model;
    }


    public ViewFactory getViewFactory(){
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {return databaseDriver;}

    public Client getClient(){ return client;}

}

