module HyperSnip {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.desktop;

    exports HyperSnip;
    opens HyperSnip to javafx.fxml;
}