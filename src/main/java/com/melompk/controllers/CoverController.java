package com.melompk.controllers;

import com.melompk.model.SongUtils;
import com.melompk.model.CoverImageUtils;
import com.melompk.model.EventHandlers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.SwipeEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CoverController implements Initializable {
    @FXML
    public ImageView coverImage;
    @FXML
    public Label songLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            EventHandlers.AddCoverController(this);
            refresh();
    }
    public void refresh(){
        coverImage.setImage(CoverImageUtils.image);
        coverImage.setFitHeight(387);
        coverImage.setFitWidth(387);
        if(SongUtils.curSong !=null) {
            songLabel.setText(SongUtils.curSong.name);
        }
        else{
            songLabel.setText("MeloZone");
        }
    }

    public void print(SwipeEvent swipeEvent) {
        System.out.println("xddddd");
    }
}
