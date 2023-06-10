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
import com.melompk.data.Song;
import com.melompk.database.DownloadUtils;
import com.melompk.database.GetData;
import com.melompk.melo.MeloApplication;
import com.melompk.model.EventHandlers;
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


public class ArtistViewController implements Initializable{

    @FXML
    private ListView<Song> artistSongsList;

    @FXML
    private ImageView artistImage = new ImageView();

    @FXML
    private Label artistNameLabel;

    @FXML
    private Button hideButton;

    private Artist artist;
    
    private Alert confirm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        EventHandlers.AddArtistViewController(this);

        hideButton.setText("exit");

        confirm = new Alert(Alert.AlertType.CONFIRMATION, "Queue is not empty, do you want to clear queue and play this song now?", ButtonType.YES, ButtonType.NO);
        
        artistSongsList.setCellFactory(param -> new ListCell<Song>() {
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

        artistSongsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (artistSongsList.getSelectionModel().getSelectedItem() == null) return;
                if (artistSongsList.getSelectionModel().getSelectedItem() instanceof Song) {
                    if (!SongQueue.IsEmpty()) {
                        confirm.showAndWait();
                        if (confirm.getResult() == ButtonType.NO) {
                            return;
                        }
                    }
                    SongQueue.Clear();
                    SongQueue.AddFront((Song) artistSongsList.getSelectionModel().getSelectedItem());
                    EventHandlers.Next.handle(new ActionEvent());
                }
            }
        });

        refresh(null);        
    }

    public void refresh(Artist artist) {
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
            List<Song> songs = GetData.GetAllSongsFromArtist(artist.artistId);
            artistSongsList.getItems().setAll(songs);
        } catch (ExecutionException | InterruptedException | IOException e) {System.out.println(e);}
    }

    public static void show(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MeloApplication.class.getResource("artist-view.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 1045, 800));
    }

    public void hide() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MeloApplication.class.getResource("front-page-view.fxml"));
        Stage stage = (Stage)hideButton.getScene().getWindow();
        stage.setScene(new Scene(fxmlLoader.load(), 1045, 800));
    }
    
}
