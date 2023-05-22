package com.melompk.melo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class MeloApplication extends Application {//Controller
    @Override
    public void start(Stage stage) throws IOException, ExecutionException, InterruptedException {
        FirebaseHandler.initialize();
        FXMLLoader fxmlLoader = new FXMLLoader(MeloApplication.class.getResource("front-page-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1045, 800);
        stage.setResizable(false);
        stage.setTitle("MeloZone");
        stage.setScene(scene);
        stage.show();

        FXMLLoader fxmlLoaderAlt = new FXMLLoader(MeloApplication.class.getResource("front-page-view.fxml"));
        Scene sceneAlt = new Scene(fxmlLoaderAlt.load(), 1045, 800);
        Stage secondStage = new Stage();
        secondStage.setScene(sceneAlt);
        secondStage.show();

        SongQueue.Init();
        //On exit
        stage.setOnCloseRequest(windowEvent -> {
            try {
                DownloadUtils.Clear();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Platform.exit();
            System.exit(0);



        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}