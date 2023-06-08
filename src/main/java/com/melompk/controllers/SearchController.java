package com.melompk.controllers;

import com.melompk.data.Album;
import com.melompk.data.Artist;
import com.melompk.data.MediaInfo;
import com.melompk.data.Song;
import com.melompk.database.DownloadUtils;
import com.melompk.database.GetData;
import com.melompk.model.EventHandlers;
import com.melompk.model.SearchUtils;
import com.melompk.model.SongQueue;
import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class SearchController implements Initializable {

    @FXML
    private Button searchButton;
    @FXML
    private Button songsOptionButton;
    @FXML
    private Button albumsOptionButton;
    @FXML
    private Button artistsOptionButton;
    @FXML
    private ListView<MediaInfo> searchResultList;

    @FXML
    private TextField searchBar;
    private Alert confirm;
    private int searchMode=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetSearchSongs();
        confirm = new Alert(Alert.AlertType.CONFIRMATION, "Queue is not empty, do you want to clear queue and play this song now?", ButtonType.YES, ButtonType.NO);
        //searchutton
        ImageView searchGraphic = new ImageView();
        searchGraphic.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/search.png").toUri().toString()));
        searchButton.setGraphic(searchGraphic);
        searchGraphic.setFitHeight(35);
        searchGraphic.setPreserveRatio(true);
        searchResultList.setCellFactory(param -> new ListCell<MediaInfo>() {
            HBox hbox = new HBox();
            VBox vbox = new VBox();
            Label titleLabel = new Label("");
            Label infoLabel = new Label("");
            MenuButton other = new MenuButton();
            Pane pane = new Pane();
            ImageView coverImage = new ImageView();
            MenuItem addFirst = new MenuItem("Add First");
            MenuItem addLast = new MenuItem("Add Last");

            {
                vbox.getChildren().addAll(titleLabel, infoLabel);
                hbox.getChildren().addAll(coverImage,vbox,pane, other);
                coverImage.setFitWidth(30);
                coverImage.setFitHeight(30);
                HBox.setHgrow(pane, Priority.ALWAYS);
                other.setText("...");
                other.setMaxWidth(30);
                other.getItems().addAll(addFirst, addLast);
                vbox.setMaxWidth(200);
                hbox.setMinWidth(270);
                hbox.setMaxWidth(270);
            }
            @Override
            protected void updateItem(MediaInfo item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    titleLabel.setText("");
                    setGraphic(null);
                } else {
                    try {
                        titleLabel.setText(item.name);
                        infoLabel.setText("");
                        if(item.imageId!=null){
                            DownloadUtils.DownloadCover(item.imageId);
                            if (DownloadUtils.IsCoverDownloaded((item).imageId+".jpg")) {
                                coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/"+ (item.imageId + ".jpg")).toUri().toString()));
                            } else {
                                coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
                            }
                        }
                        else{
                            coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
                        }
                        if(!(item instanceof Artist)){
                            infoLabel.setText(item.artistName);
                            infoLabel.setStyle("-fx-text-fill: #909090");
                        }
                        if(!(item instanceof Song)){
                            other.setVisible(false);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    setGraphic(hbox);
                    addFirst.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            SongQueue.AddFront((Song) item);
                        }
                    });
                    addLast.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            SongQueue.AddBack((Song) item);
                        }
                    });
                }
            }
        });

        searchResultList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (searchResultList.getSelectionModel().getSelectedItem() == null) return;
                if (searchResultList.getSelectionModel().getSelectedItem() instanceof Song) {
                    if (!SongQueue.IsEmpty()) {
                        confirm.showAndWait();
                        if (confirm.getResult() == ButtonType.NO) {
                            return;
                        }
                    }
                    SongQueue.Clear();
                    SongQueue.AddFront((Song) searchResultList.getSelectionModel().getSelectedItem());
                    EventHandlers.Next.handle(new ActionEvent());
                }
                
                if (searchResultList.getSelectionModel().getSelectedItem() instanceof Album) {
                    try {
                        AlbumViewController.show((Stage)((Node)mouseEvent.getSource()).getScene().getWindow());
                        for (AlbumViewController contr : EventHandlers.albumViewControllers) {
                            contr.refresh((Album)searchResultList.getSelectionModel().getSelectedItem());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public List<MediaInfo> searchList(String query) throws ExecutionException, InterruptedException {//
//        List<String> queryArray = Arrays.asList(query.trim().split(" "));
//
//        return GetData.GetAllSongs().stream().filter(input -> {
//            return queryArray.stream().allMatch(word -> input.title.toLowerCase().contains(word.toLowerCase()));
//        }).collect(Collectors.toList());
        if(searchMode==0){
            return SearchUtils.SearchSongs(query);
        }
        if(searchMode==1){
            return SearchUtils.SearchAlbums(query);
        }
        return SearchUtils.SearchArtists(query);
    }

    public void search() throws ExecutionException, InterruptedException {
        searchResultList.getItems().clear();
        searchResultList.getItems().addAll((searchList(searchBar.getText())));
    }
    public void ClearButtons(){
        songsOptionButton.setStyle("-fx-background-color: #808080");
        albumsOptionButton.setStyle("-fx-background-color: #808080");
        artistsOptionButton.setStyle("-fx-background-color: #808080");
    }
    public void SetSearchSongs(){
        ClearButtons();
        searchMode=0;
        songsOptionButton.setStyle("-fx-background-color: #303030");
    }
    public void SetSearchAlbums(){
        ClearButtons();
        searchMode=1;
        albumsOptionButton.setStyle("-fx-background-color: #303030");
    }
    public void SetSearchArtists(){
        ClearButtons();
        searchMode=2;
        artistsOptionButton.setStyle("-fx-background-color: #303030");
    }
}
