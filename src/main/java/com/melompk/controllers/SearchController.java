package com.melompk.controllers;

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
    private ListView<MediaInfo> searchResultList;

    @FXML
    private TextField searchBar;
    private Alert confirm;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirm = new Alert(Alert.AlertType.CONFIRMATION, "Queue is not empty, do you want to clear queue and play this song now?", ButtonType.YES, ButtonType.NO);
        searchResultList.setCellFactory(param -> new ListCell<MediaInfo>() {
            HBox hbox = new HBox();
            VBox vbox = new VBox();
            Label titleLabel = new Label("(empty)");
            Label infoLabel = new Label("(empty)");
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

                if (empty || item == null || ((Song) item).title == null) {
                    titleLabel.setText("");
                    setGraphic(null);
                } else {
                    try {
                        DownloadUtils.DownloadCover(((Song) item).albumId);

                        if (DownloadUtils.IsCoverDownloaded(((Song) item).albumId+".jpg")) {
                            coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/"+ ((Song) item).albumId + ".jpg").toUri().toString()));
                        } else {
                            coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
                        }
                        titleLabel.setText(((Song) item).title);
                        infoLabel.setText(((Song) item).artistId);
                        infoLabel.setStyle("-fx-text-fill: #909090");
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
                if(!SongQueue.IsEmpty()) {
                    confirm.showAndWait();
                    if (confirm.getResult() == ButtonType.NO) {
                        return;
                    }
                }
                SongQueue.Clear();
                SongQueue.AddFront((Song) searchResultList.getSelectionModel().getSelectedItem());
                EventHandlers.Next.handle(new ActionEvent());
            }
        });
    }

    public List<MediaInfo> searchList(String query) throws ExecutionException, InterruptedException {
        if (Objects.equals(query, "*")) return new ArrayList<>(GetData.GetAllSongs());
//
//        List<String> queryArray = Arrays.asList(query.trim().split(" "));
//
//        return GetData.GetAllSongs().stream().filter(input -> {
//            return queryArray.stream().allMatch(word -> input.title.toLowerCase().contains(word.toLowerCase()));
//        }).collect(Collectors.toList());
        return SearchUtils.SearchSongs(query);
    }

    public void search() throws ExecutionException, InterruptedException {
        searchResultList.getItems().clear();
        searchResultList.getItems().addAll((searchList(searchBar.getText())));
    }
}
