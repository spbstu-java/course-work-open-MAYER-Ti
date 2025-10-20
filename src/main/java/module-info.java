module com.lyutov.labs_fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lyutov.labs_fx.ui to javafx.fxml;
    opens com.lyutov.labs_fx.features.lab1 to javafx.fxml;
    opens com.lyutov.labs_fx.features.lab2 to javafx.fxml;
    opens com.lyutov.labs_fx.features.lab3 to javafx.fxml;
    opens com.lyutov.labs_fx.features.lab4 to javafx.fxml;
    exports com.lyutov.labs_fx;
}