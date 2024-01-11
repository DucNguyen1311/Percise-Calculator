module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires ch.obermuhlner.math.big;


    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
}