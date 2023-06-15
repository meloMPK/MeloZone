package com.melompk.melo;

import com.melompk.database.FirebaseHandler;
import com.melompk.model.CoverImageUtils;
import com.melompk.model.SongQueue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class MeloApplication extends Application {//Controller

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException, ExecutionException, InterruptedException {
        FirebaseHandler.initialize();
        CoverImageUtils.init();
        SongQueue.Init();
        FXMLLoader fxmlLoader = new FXMLLoader(MeloApplication.class.getResource("front-page-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1045, 800);
        stage.getIcons().add(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/melologo.png").toUri().toString()));
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
}