package com.melompk.controllers;

import com.melompk.model.EventHandlers;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public AnchorPane pane;
    public AnchorPane artistView;
    public AnchorPane albumView;
    public AnchorPane coverTitleView;
    public void SetAlbumView(){
        albumView.setVisible(true);
        artistView.setVisible(false);
        coverTitleView.setVisible(false);
    }
    public void SetArtistView(){
        albumView.setVisible(false);
        artistView.setVisible(true);
        coverTitleView.setVisible(false);
    }
    public void SetCoverTitleView(){
        albumView.setVisible(false);
        artistView.setVisible(false);
        coverTitleView.setVisible(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetCoverTitleView();
        EventHandlers.mainControllers.add(this);
    }
}
