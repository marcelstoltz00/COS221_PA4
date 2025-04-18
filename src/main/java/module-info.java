module com.example.northwind_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop; 
    requires java.sql;


    opens com.example.northwind_gui to javafx.fxml;
    exports com.example.northwind_gui;
}