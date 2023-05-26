package com.melompk.melo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public ImageView coverImage;
    @FXML
    public Label songLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            EventHandlers.AddMainController(this);
            refresh();
    }
    public void refresh(){
        coverImage.setImage(CoverImageUtils.image);
        coverImage.setFitHeight(387);
        coverImage.setFitWidth(387);
        if(SongUtils.curSong !=null) {
            songLabel.setText(SongUtils.curSong.title);
        }
        else{
            songLabel.setText("MeloZone");
        }
    }
}
