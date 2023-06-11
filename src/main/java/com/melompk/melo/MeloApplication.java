package com.melompk.melo;

import com.melompk.controllers.AlbumViewController;
import com.melompk.database.DownloadUtils;
import com.melompk.database.FirebaseHandler;
import com.melompk.model.CoverImageUtils;
import com.melompk.model.EventHandlers;
import com.melompk.model.SongQueue;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MeloApplication extends Application {//Controller
    @Override
    public void start(Stage stage) throws IOException, ExecutionException, InterruptedException {
        FirebaseHandler.initialize();
        CoverImageUtils.init();
        SongQueue.Init();
        FXMLLoader fxmlLoader = new FXMLLoader(MeloApplication.class.getResource("front-page-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1045, 800);
        stage.setResizable(false);
        stage.setTitle("MeloZone");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
        /*FXMLLoader fxmlLoaderAlt = new FXMLLoader(MeloApplication.class.getResource("front-page-view.fxml"));
        Scene sceneAlt = new Scene(fxmlLoaderAlt.load(), 1045, 800);
        Stage secondStage = new Stage();
        secondStage.setScene(sceneAlt);
        secondStage.show();*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}