package com.melompk.controllers;

import com.melompk.data.Album;
import com.melompk.data.Artist;
import com.melompk.data.MediaInfo;
import com.melompk.data.Song;
import com.melompk.database.DownloadUtils;
import com.melompk.model.EventHandlers;
import com.melompk.model.SearchUtils;
import com.melompk.model.SongQueue;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

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
    private int searchMode = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetSearchSongs();
        confirm = new Alert(Alert.AlertType.CONFIRMATION, "Queue is not empty, do you want to clear queue and play this song now?", ButtonType.YES, ButtonType.NO);
        InitializeButtons();
        InitializeSearchResultList();
    }

    public List<MediaInfo> searchList(String query) throws ExecutionException, InterruptedException {//
//        List<String> queryArray = Arrays.asList(query.trim().split(" "));
//
//        return GetData.GetAllSongs().stream().filter(input -> {
//            return queryArray.stream().allMatch(word -> input.title.toLowerCase().contains(word.toLowerCase()));
//        }).collect(Collectors.toList());
        if (searchMode == 0) {
            return SearchUtils.SearchSongs(query);
        }
        if (searchMode == 1) {
            return SearchUtils.SearchAlbums(query);
        }
        return SearchUtils.SearchArtists(query);
    }

    public void search() throws ExecutionException, InterruptedException {
        searchResultList.getItems().clear();
        searchResultList.getItems().addAll((searchList(searchBar.getText())));
    }

    public void ClearButtons() {
        albumsOptionButton.setDisable(false);
        artistsOptionButton.setDisable(false);
        songsOptionButton.setDisable(false);
    }

    public void SetSearchSongs() {
        ClearButtons();
        searchMode = 0;
        songsOptionButton.setDisable(true);
    }

    public void SetSearchAlbums() {
        ClearButtons();
        searchMode = 1;
        albumsOptionButton.setDisable(true);
    }

    public void SetSearchArtists() {
        ClearButtons();
        searchMode = 2;
        artistsOptionButton.setDisable(true);
    }

    void InitializeButtons() {
        //searchutton
        ImageView searchGraphic = new ImageView();
        searchGraphic.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/search.png").toUri().toString()));
        searchButton.setGraphic(searchGraphic);
        searchGraphic.setFitHeight(40);
        searchGraphic.setPreserveRatio(true);
    }

    void InitializeSearchResultList() {
        searchResultList.setCellFactory(param -> new ListCell<MediaInfo>() {
            final HBox hbox = new HBox();
            final VBox vbox = new VBox();
            final Label titleLabel = new Label("");
            final Label infoLabel = new Label("");
            final MenuButton other = new MenuButton();
            final Pane pane = new Pane();
            final ImageView coverImage = new ImageView();
            final MenuItem addFirst = new MenuItem("Add First");
            final MenuItem addLast = new MenuItem("Add Last");

            {
                vbox.getChildren().addAll(titleLabel, infoLabel);
                hbox.getChildren().addAll(coverImage, vbox, pane, other);
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
                titleLabel.setMaxWidth(140);
                if (empty || item == null) {
                    titleLabel.setText("");
                    setGraphic(null);
                } else {
                    try {
                        titleLabel.setText(item.name);
                        infoLabel.setText("");
                        if (item.imageId != null) {
                            if (item instanceof Artist) {
                                DownloadUtils.DownloadArtistImage(item.imageId);
                                if (DownloadUtils.IsArtistImageDownloaded((item).imageId + ".jpg")) {
                                    coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/ArtistImages/" + (item.imageId + ".jpg")).toUri().toString()));
                                } else {
                                    coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
                                }
                            } else {
                                DownloadUtils.DownloadCover(item.imageId);
                                if (DownloadUtils.IsCoverDownloaded((item).imageId + ".jpg")) {
                                    coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/" + (item.imageId + ".jpg")).toUri().toString()));
                                } else {
                                    coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
                                }
                            }
                        } else {
                            coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
                        }
                        if (!(item instanceof Artist)) {
                            infoLabel.setText(item.artistName);
                            infoLabel.setStyle("-fx-text-fill: #909090");
                        }
                        if (!(item instanceof Song)) {
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
                    EventHandlers.RefreshAlbumView((Album) searchResultList.getSelectionModel().getSelectedItem());
                    EventHandlers.SetAlbumView(false);
                }

                if (searchResultList.getSelectionModel().getSelectedItem() instanceof Artist) {
                    EventHandlers.RefreshArtistView((Artist) searchResultList.getSelectionModel().getSelectedItem());
                    EventHandlers.SetArtistView();
                }
            }
        });
    }
}
