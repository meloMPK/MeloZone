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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
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
    ImageView playGraphic;
    ImageView pauseGraphic;
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

        //playButton
        playGraphic = new ImageView();
        pauseGraphic = new ImageView();
        playGraphic.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/play.png").toUri().toString()));
        pauseGraphic.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/pause.png").toUri().toString()));
        playButton.setGraphic(playGraphic);
        playGraphic.setFitHeight(64);
        pauseGraphic.setFitHeight(64);
        playGraphic.setPreserveRatio(true);
        pauseGraphic.setPreserveRatio(true);

        //prevButton
        ImageView prevGraphic = new ImageView();
        prevGraphic.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/prev.png").toUri().toString()));
        prevButton.setGraphic(prevGraphic);
        prevGraphic.setFitHeight(64);
        prevGraphic.setPreserveRatio(true);

        //nextButton
        ImageView nextGrahpic = new ImageView();
        nextGrahpic.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/next.png").toUri().toString()));
        nextButton.setGraphic(nextGrahpic);
        nextGrahpic.setFitHeight(64);
        nextGrahpic.setPreserveRatio(true);

        //reset
        ImageView resetGraphic = new ImageView();
        resetGraphic.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/reset.png").toUri().toString()));
        resetButton.setGraphic(resetGraphic);
        resetGraphic.setFitHeight(20);
        resetGraphic.setPreserveRatio(true);

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
        if(isPlaying){
            playButton.setGraphic(pauseGraphic);
        }
        else{
            playButton.setGraphic(playGraphic);
        }
        if(isPlaying && SongUtils.curSong!=null) beginTimer();
        else pauseTimer();
        SongUtils.player.setVolume(volumeSlider.getValue() * 0.01);
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