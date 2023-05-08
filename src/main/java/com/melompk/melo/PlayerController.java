package com.melompk.melo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class PlayerController implements Initializable {//View

    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton, pauseButton, resetButton, previousButton, nextButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;

    private Timer timer;
    private TimerTask task;
    private boolean isPlaying;

    public void playMedia() throws ExecutionException, InterruptedException {
        SongUtils.Play();
    }

    public void pauseMedia() {
        SongUtils.Pause();
    }

    public void nextMedia() {}

    public void prevMedia() {}

    public void resetMedia() {}

    public void beginTimer() {}

    public void cancelTimer() {}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}