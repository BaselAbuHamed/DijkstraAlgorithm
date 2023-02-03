module com.comp336.projectalgo3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.comp336.projectalgo3 to javafx.fxml;
    exports com.comp336.projectalgo3;
}