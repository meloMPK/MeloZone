package com.melompk.melo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.concurrent.ExecutionException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws ExecutionException, InterruptedException {
        SongUtils.Play(DownloadUtils.DownloadSong(GetData.FindSongId("Jacek Kaczmarski","Mury", "Mury")));
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}