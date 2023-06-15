package com.melompk.controllers;

import com.melompk.model.EventHandlers;
import com.melompk.model.SongUtils;
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
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerController implements Initializable {//View

    @FXML
    public Slider volumeSlider, progressSlider;
    public Label songLabel;
    ImageView playGraphic, pauseGraphic, mutedGraphic, unmutedGraphic;
    @FXML
    private Pane pane;
    @FXML
    private Button playButton, prevButton, nextButton, muteButton;
    private Timer timer;
    private TimerTask task;
    private boolean isPlaying = false;
    private boolean muted = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!EventHandlers.playerControllers.isEmpty())
            volumeSlider.valueProperty().bindBidirectional(EventHandlers.playerControllers.getFirst().volumeSlider.valueProperty());
        EventHandlers.AddPlayerController(this);
        InitializeButtons();
        InitalizeSliders();
    }

    public void refresh() {
        isPlaying = SongUtils.isPlaying;
        if (isPlaying) {
            playButton.setGraphic(pauseGraphic);
        } else {
            playButton.setGraphic(playGraphic);
        }

        if (isPlaying && SongUtils.curSong != null) beginTimer();
        else pauseTimer();
        SongUtils.player.setMute(muted);
        SongUtils.player.setVolume(volumeSlider.getValue() * 0.01);
    }

    public void muteRefresh() {
        muted = SongUtils.player.isMute();
        if (muted) muteButton.setGraphic(mutedGraphic);
        else muteButton.setGraphic(unmutedGraphic);
    }

    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                isPlaying = true;
                double current = SongUtils.player.getCurrentTime().toSeconds();
                double end = SongUtils.curMedia.getDuration().toSeconds();
                progressSlider.setValue((current / end) * 100);
                if (current / end == 1) cancelTimer();
            }
        };

        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    void cancelTimer() {
        progressSlider.setValue(0);
        if (timer == null) return;
        timer.cancel();
    }

    private void pauseTimer() {
        if (timer == null) return;
        timer.cancel();
    }

    void SetImageDefaultSettings(ImageView imv, Image img) {
        imv.setFitHeight(40);
        imv.setPreserveRatio(true);
        imv.setImage(img);
    }

    void InitializeButtons() {
        nextButton.setOnAction(EventHandlers.Next);
        playButton.setOnAction(EventHandlers.Play);
        prevButton.setOnAction(EventHandlers.Prev);
        muteButton.setOnAction(EventHandlers.Mute);

        //muteButton
        mutedGraphic = new ImageView();
        unmutedGraphic = new ImageView();
        muteButton.setGraphic(unmutedGraphic);
        SetImageDefaultSettings(mutedGraphic, new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/muted.png").toUri().toString()));
        SetImageDefaultSettings(unmutedGraphic, new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/unmuted.png").toUri().toString()));

        //playButton
        playGraphic = new ImageView();
        pauseGraphic = new ImageView();
        playButton.setGraphic(playGraphic);
        SetImageDefaultSettings(pauseGraphic, new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/pause.png").toUri().toString()));
        SetImageDefaultSettings(playGraphic, new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/play.png").toUri().toString()));

        //prevButton
        ImageView prevGraphic = new ImageView();
        prevButton.setGraphic(prevGraphic);
        SetImageDefaultSettings(prevGraphic, new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/prev.png").toUri().toString()));

        //nextButton
        ImageView nextGrahpic = new ImageView();
        nextButton.setGraphic(nextGrahpic);
        SetImageDefaultSettings(nextGrahpic, new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/next.png").toUri().toString()));
    }

    void InitalizeSliders() {
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (SongUtils.curSong != null) {
                    SongUtils.player.setVolume(volumeSlider.getValue() * 0.01);
                }
            }
        });

        progressSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ov) {
                if (progressSlider.isPressed() && SongUtils.curSong != null) {
                    SongUtils.player.seek(
                            SongUtils.player.getMedia().getDuration().multiply(progressSlider.getValue() * 0.01));
                }
            }
        });
    }
}