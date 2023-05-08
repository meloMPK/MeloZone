package com.melompk.melo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MeloApplication extends Application {//Controller
    @Override
    public void start(Stage stage) throws IOException, ExecutionException, InterruptedException {
        FirebaseHandler.initialize();
        FXMLLoader fxmlLoader = new FXMLLoader(MeloApplication.class.getResource("front-page-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 600);
        stage.setResizable(false);
        stage.setTitle("MeloZone");
        stage.setScene(scene);
        stage.show();

        //On exit
        stage.setOnCloseRequest(windowEvent -> {
            DownloadUtils.Clear();
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}