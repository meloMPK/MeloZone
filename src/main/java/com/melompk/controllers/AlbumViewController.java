package com.melompk.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.melompk.data.Album;
import com.melompk.data.Artist;
import com.melompk.data.Song;
import com.melompk.database.DownloadUtils;
import com.melompk.database.GetData;
import com.melompk.melo.MeloApplication;
import com.melompk.model.EventHandlers;
import com.melompk.model.SearchUtils;
import com.melompk.model.SongQueue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AlbumViewController implements Initializable{
    @FXML

    public Label artistNameLabel;
    @FXML
    private ListView<Song> albumSongsList;

    @FXML
    private ImageView albumCoverImage = new ImageView();

    @FXML
    private Label albumNameLabel;

    @FXML
    private Button hideButton;

    private Album album;
    
    private Alert confirm;
    public boolean isReferencedFromArtist=false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        EventHandlers.AddAlbumViewController(this);

        hideButton.setText("exit");

        confirm = new Alert(Alert.AlertType.CONFIRMATION, "Queue is not empty, do you want to clear queue and play this now?", ButtonType.YES, ButtonType.NO);
        
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
                    titleLabel.setText(item.name);
                    infoLabel.setText(String.valueOf(item.albumPosition));
                    infoLabel.setStyle("-fx-text-fill: #909090");
                    setGraphic(hbox);
                }
            }
        });

        albumSongsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (albumSongsList.getSelectionModel().getSelectedItem() == null) return;
                if (albumSongsList.getSelectionModel().getSelectedItem() instanceof Song) {
                    if (!SongQueue.IsEmpty()) {
                        confirm.showAndWait();
                        if (confirm.getResult() == ButtonType.NO) {
                            return;
                        }
                    }
                    SongQueue.Clear();
                    SongQueue.AddFront((Song) albumSongsList.getSelectionModel().getSelectedItem());
                    EventHandlers.Next.handle(new ActionEvent());
                }
            }
        });

        refresh(null);        
    }

    public void refresh(Album album) {
        if(album==null) {
            albumCoverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
            albumNameLabel.setText("");
            artistNameLabel.setText("");
            return;
        }
        this.album = album;
        try {
            DownloadUtils.DownloadCover(album.albumId);
            if(!DownloadUtils.IsCoverDownloaded(album.albumId+".jpg")) albumCoverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
            else albumCoverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/" + album.albumId + ".jpg").toUri().toString()));
            albumNameLabel.setText(album.name);
            artistNameLabel.setText(album.artistName);
            artistNameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    EventHandlers.SetArtistView();
                    try {
                        EventHandlers.RefreshArtistView(SearchUtils.SearchArtistsById(album.artistId));
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            List<Song> songs = GetData.GetAllSongsFromAlbum(album.albumId);
            albumSongsList.getItems().setAll(songs);
        } catch (ExecutionException | InterruptedException | IOException e) {System.out.println(e);}
    }

    public void hide() throws IOException {
        if(isReferencedFromArtist) EventHandlers.SetArtistView();
        else EventHandlers.SetCoverTitleView();
    }

    public void Play() {
        if (!SongQueue.IsEmpty()) {
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.NO) {
                return;
            }
        }
        SongQueue.Clear();
        AddToQueue();
        EventHandlers.Next.handle(new ActionEvent());
    }

    public void AddToQueue() {
        SongQueue.AddAll(new LinkedList<>(albumSongsList.getItems()));
    }
}
