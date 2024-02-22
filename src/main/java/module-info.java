module com.example.practica5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    exports com.example.practica5.ejercicio1;
    opens com.example.practica5.ejercicio1 to javafx.fxml;
}