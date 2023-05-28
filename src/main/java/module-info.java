module com.melompk.melo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.auth.oauth2;
    requires firebase.admin;
    requires com.google.auth;
    requires google.cloud.firestore;
    requires com.google.api.apicommon;
    requires google.cloud.core;
    requires google.cloud.storage;
    opens com.melompk.melo to javafx.fxml;
    exports com.melompk.melo;
    exports com.melompk.data;
    opens com.melompk.data to javafx.fxml;
    exports com.melompk.model;
    opens com.melompk.model to javafx.fxml;
    exports com.melompk.database;
    opens com.melompk.database to javafx.fxml;
    exports com.melompk.controllers;
    opens com.melompk.controllers to javafx.fxml;
}