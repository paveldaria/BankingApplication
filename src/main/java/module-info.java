module org.example.mazebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens org.example.mazebank to javafx.fxml;
    exports org.example.mazebank;
    exports org.example.mazebank.Controllers.Login;
    exports org.example.mazebank.Controllers.Admin;
    exports org.example.mazebank.Controllers.Client;
    exports org.example.mazebank.Models;
    exports org.example.mazebank.Views;
}