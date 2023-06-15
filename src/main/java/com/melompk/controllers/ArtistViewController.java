package com.melompk.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.melompk.data.Album;
import com.melompk.data.Artist;
import com.melompk.database.DownloadUtils;
import com.melompk.database.GetData;
import com.melompk.model.EventHandlers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class ArtistViewController implements Initializable{
    @FXML
    public TilePane artistAlbumsList;
    @FXML
    private ImageView artistImage = new ImageView(), hideGraphic;

    @FXML
    private Label artistNameLabel;

    @FXML
    private Button hideButton;

    private Artist artist;
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InitializeButtons();
        EventHandlers.AddArtistViewController(this);
        refresh(null);
        artistAlbumsList.setVgap(3);
        artistAlbumsList.setHgap(3);
        artistAlbumsList.setPrefColumns(10);
    }

    public void refresh(Artist artist) {
        artistAlbumsList.getChildren().clear();
        if(artist==null) {
            artistImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
            artistNameLabel.setText("");
            return;
        }
        this.artist = artist;
        try {
            DownloadUtils.DownloadArtistImage(artist.artistId);
            if(!DownloadUtils.IsArtistImageDownloaded(artist.artistId+".jpg")) artistImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
            else artistImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/ArtistImages/" + artist.artistId + ".jpg").toUri().toString()));
            artistNameLabel.setText(artist.name);
            List<Album> albums = GetData.GetAllAlbumsFromArtist(artist.artistId);
            for(Album cur: albums){
                artistAlbumsList.getChildren().add(CreateNode(cur));
            }
        } catch (ExecutionException | InterruptedException | IOException e) {System.out.println(e);}
    }
    public Node CreateNode(Album item) throws IOException {

        VBox box = new VBox();
        box.setPrefSize(200,200);
        box.setAlignment(Pos.CENTER);
        ImageView coverImage = new ImageView();
        coverImage.setFitWidth(150);
        coverImage.setFitHeight(150);
        Label albumName=new Label(item.name);
        albumName.setStyle("-fx-text-fill: white");
        albumName.setMinHeight(40);
        DownloadUtils.DownloadCover(item.imageId);
        if (DownloadUtils.IsCoverDownloaded((item).imageId+".jpg")) {
            coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/"+ (item.imageId + ".jpg")).toUri().toString()));
        } else {
            coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
        }
        box.getChildren().addAll(coverImage,albumName);
        box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EventHandlers.RefreshAlbumView(item);
                EventHandlers.SetAlbumView(true);
            }
        });

        return box;
    }
    public static void show(Stage stage) throws IOException{
        EventHandlers.SetArtistView();
    }

    public void hide() throws IOException {
        EventHandlers.SetCoverTitleView();
    }
    public void InitializeButtons(){
        hideGraphic = new ImageView();
        hideGraphic.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/hide.png").toUri().toString()));
        hideButton.setGraphic(hideGraphic);
        hideGraphic.setFitHeight(23);
        hideGraphic.setPreserveRatio(true);
    }
}
