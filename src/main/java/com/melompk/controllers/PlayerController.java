package com.melompk.controllers;

import com.melompk.database.DownloadUtils;
import com.melompk.model.SongUtils;
import com.melompk.model.EventHandlers;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerController implements Initializable {//View

    @FXML
    private Pane pane;
    @FXML
    private Button playButton, resetButton, prevButton, nextButton;
    @FXML
    public Slider volumeSlider, progressSlider;
    public Label songLabel;
    private Timer timer;
    private TimerTask task;
    private boolean isPlaying = false;
    String[] playAndStop = {"PLAY", "STOP"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!EventHandlers.playerControllers.isEmpty())
            volumeSlider.valueProperty().bindBidirectional(EventHandlers.playerControllers.getFirst().volumeSlider.valueProperty());
        EventHandlers.AddPlayerController(this);
        nextButton.setOnAction(EventHandlers.Next);
        playButton.setOnAction(EventHandlers.Play);
        prevButton.setOnAction(EventHandlers.Prev);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(SongUtils.curSong!=null) {
                    SongUtils.player.setVolume(volumeSlider.getValue() * 0.01);
                }
            }
        });

        progressSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ov) {
                if (progressSlider.isPressed() && SongUtils.curSong!=null) {
                    SongUtils.player.seek(
                            SongUtils.player.getMedia().getDuration().multiply(progressSlider.getValue()*0.01));
                }
            }
        });
    }
    public void refresh(){
        isPlaying=SongUtils.isPlaying;
        playButton.setText(playAndStop[isPlaying ? 1 : 0]);
        if(isPlaying && SongUtils.curSong!=null) beginTimer();
        else pauseTimer();
    }
    public void resetMedia() throws IOException {
        progressSlider.setValue(0);
        SongUtils.Pause();
        songLabel.setText("Melozone");
        DownloadUtils.Clear();
    }

    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                isPlaying = true;
                double current = SongUtils.player.getCurrentTime().toSeconds();
                double end = SongUtils.curMedia.getDuration().toSeconds();
                progressSlider.setValue((current/end)*100);
                if (current/end == 1) cancelTimer();
            }
        };

        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    void cancelTimer() {
        progressSlider.setValue(0);
        if(timer==null) return;
        timer.cancel();
    }

    private void pauseTimer() {
        if(timer==null) return;
        timer.cancel();
    }
}