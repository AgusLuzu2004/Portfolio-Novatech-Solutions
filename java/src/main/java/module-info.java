module com.novatech {

    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.desktop;
    requires javafx.base;
    requires org.apache.poi.ooxml;
    requires com.github.librepdf.openpdf;
    requires org.slf4j;

    opens com.novatech.controller to javafx.fxml;

    opens com.novatech.model to javafx.base;

    exports com.novatech;

}
