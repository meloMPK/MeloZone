module com.melompk.melo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.melompk.melo to javafx.fxml;
    exports com.melompk.melo;
}