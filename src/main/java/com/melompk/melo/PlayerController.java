package com.melompk.melo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
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
    private Button playButton, resetButton, previousButton, nextButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private ImageView coverImage;

    private Timer timer;
    private TimerTask task;
    private boolean isPlaying = false;
    String[] playAndStop = {"PLAY", "STOP"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                SongUtils.player.setVolume(volumeSlider.getValue() * 0.01);
            }
        });
        try {
            CoverImageUtils.init(coverImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void playSong() throws ExecutionException, InterruptedException, IOException {
        SongUtils.Play();
        CoverImageUtils.refresh();
        SongUtils.player.setVolume(volumeSlider.getValue() * 0.01);
        SongUtils.player.setOnEndOfMedia(()-> {
            try {
                this.nextMedia();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        beginTimer();
    }

    public void playMedia() throws ExecutionException, InterruptedException, IOException {
        if (!isPlaying) {
            playSong();
        } else {
            pauseTimer();
            SongUtils.Pause();
        }

        isPlaying = !isPlaying;
        playButton.setText(playAndStop[isPlaying ? 1 : 0]);
        songLabel.setText(SongUtils.getCurrentSong().title);
    }

    public void nextMedia() throws ExecutionException, InterruptedException, IOException {
        SongUtils.Pause();
        SongUtils.NextSong();
        CoverImageUtils.refresh();
        cancelTimer();
        if (isPlaying) {
            playSong();
            beginTimer();
        }

        songLabel.setText(SongUtils.getCurrentSong().title);
    }
    public void prevMedia() throws ExecutionException, InterruptedException, IOException {
        SongUtils.Pause();
        SongUtils.PrevSong();
        CoverImageUtils.refresh();
        cancelTimer();
        if (isPlaying) {
            playSong();
            beginTimer();
        }

        songLabel.setText(SongUtils.getCurrentSong().title);
    }

    public void resetMedia() throws IOException {
        songProgressBar.setProgress(0);
        SongUtils.Pause();
        songLabel.setText("SONGS DELETED");
        DownloadUtils.Clear();
    }

    private void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                isPlaying = true;
                double current = SongUtils.player.getCurrentTime().toSeconds();
                double end = SongUtils.curMedia.getDuration().toSeconds();
                songProgressBar.setProgress(current/end);

                if (current/end == 1) cancelTimer();
            }
        };

        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    private void cancelTimer() {
        songProgressBar.setProgress(0);
        timer.cancel();
    }

    private void pauseTimer() {
        timer.cancel();
    }
}