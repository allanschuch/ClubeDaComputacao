module allan.clubedacomputacao {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens allan.clubedacomputacao to javafx.fxml;
    exports allan.clubedacomputacao;
}
