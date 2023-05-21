package com.melompk.melo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ImageView coverImage;

    @FXML
    private Label songLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CoverImageUtils.init(coverImage);
            PlayerController.assignSongLabel(songLabel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
