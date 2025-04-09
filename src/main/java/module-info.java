module com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi to javafx.fxml;
    exports com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi;
}