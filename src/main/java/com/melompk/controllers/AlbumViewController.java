package com.melompk.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.melompk.data.Album;
import com.melompk.data.Song;
import com.melompk.database.DownloadUtils;
import com.melompk.database.GetData;
import com.melompk.melo.MeloApplication;
import com.melompk.model.EventHandlers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AlbumViewController implements Initializable{

    @FXML
    private Parent root;

    @FXML
    private ListView<Song> albumSongsList;

    @FXML
    private ImageView albumCoverImage = new ImageView();

    private Album album;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        EventHandlers.AddAlbumViewController(this);
        
        albumSongsList.setCellFactory(param -> new ListCell<Song>() {
            HBox hbox = new HBox();
            VBox vbox = new VBox();
            Label titleLabel = new Label("(empty)");
            Label infoLabel = new Label("(empty)");
            Button button = new Button("...");
            Pane pane = new Pane();

            {
                vbox.getChildren().addAll(titleLabel, infoLabel);
                hbox.getChildren().addAll(vbox,pane, button);
                HBox.setHgrow(pane, Priority.ALWAYS);
                vbox.setMaxWidth(200);
                hbox.setMinWidth(270);
                hbox.setMaxWidth(270);
            }
            
            @Override
            protected void updateItem(Song item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || ((Song) item).name == null) {
                    titleLabel.setText("");
                    setGraphic(null);
                } else {
                    titleLabel.setText(((Song) item).name);
                    infoLabel.setText(((Song) item).artistId);
                    infoLabel.setStyle("-fx-text-fill: #909090");
                    setGraphic(hbox);
                }
            }
        });

        // refresh(new Album("AMPPZ", "2", "2"));
        refresh(null);
        try {
            show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public void refresh(Album album) {
        if(album==null) {
            albumCoverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
            return;
        }
        this.album = album;
        try {
            DownloadUtils.DownloadCover(album.albumId);
            albumCoverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/" + album.albumId + ".jpg").toUri().toString()));
            System.out.println(albumCoverImage.getImage().getUrl());
            List<Song> songs = GetData.GetAllSongsFromAlbum(album.albumId);
            albumSongsList.getItems().setAll(songs);
        } catch (ExecutionException | InterruptedException | IOException e) {System.out.println(e);}
    }

    public void show() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MeloApplication.class.getResource("album-view.fxml"));
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(fxmlLoader.load(), 1045, 800));
    }
    
}
