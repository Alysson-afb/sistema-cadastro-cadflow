module com.mycompany.cadflow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens com.mycompany.cadflow to javafx.fxml;
    exports com.mycompany.cadflow;
    requires jbcrypt;
    opens model.classes to javafx.base;
    requires com.github.librepdf.openpdf;
    requires java.desktop;
}