package com.melompk.controllers;

import com.melompk.model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class CoverController implements Initializable {
    @FXML
    public ImageView coverImage;
    @FXML
    public Label songLabel;
    @FXML
    public Label artistLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            EventHandlers.AddCoverController(this);
            refresh();
            artistLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(SongUtils.curSong!=null) {
                        EventHandlers.SetArtistView();
                        try {
                            EventHandlers.RefreshArtistView(SearchUtils.SearchArtistsById(SongUtils.curSong.artistId));
                        } catch (ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            coverImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(SongUtils.curSong!=null) {
                        EventHandlers.SetAlbumView(false);
                        try {
                            EventHandlers.RefreshAlbumView(SearchUtils.SearchAlbumById(SongUtils.curSong.albumId));
                        } catch (ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        });
    }
    public void refresh(){
        coverImage.setImage(CoverImageUtils.image);
        coverImage.setFitHeight(387);
        coverImage.setFitWidth(387);
        if(SongUtils.curSong !=null) {
            songLabel.setText(SongUtils.curSong.name);
            artistLabel.setText(SongUtils.curSong.artistName);
        }
        else{
            songLabel.setText("MeloZone");
            artistLabel.setText("MeloMPK");
        }
    }

    public void print(SwipeEvent swipeEvent) {
        System.out.println("xddddd");
    }
}
