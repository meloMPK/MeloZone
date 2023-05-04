package com.melompk.melo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        SongUtils.Play(DownloadUtils.DownloadSong("Mury.mp3"));
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}