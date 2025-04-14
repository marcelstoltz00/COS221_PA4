module com.example.northwind_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.northwind_gui to javafx.fxml;
    exports com.example.northwind_gui;
}