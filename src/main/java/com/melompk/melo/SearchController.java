package com.melompk.melo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchResultList.setCellFactory(param -> new ListCell<MediaInfo>() {
            private ImageView coverImage = new ImageView();

            @Override
            protected void updateItem(MediaInfo item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || ((Song) item).title == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        DownloadUtils.DownloadCover(((Song) item).albumId);

                        if (DownloadUtils.IsCoverDownloaded(((Song) item).albumId+".jpg")) {
                            coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/"+ ((Song) item).albumId + ".jpg").toUri().toString()));
                        } else {
                            coverImage.setImage(new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString()));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    coverImage.setFitHeight(30);
                    coverImage.setFitWidth(30);
                    setText(((Song) item).title);
                    setGraphic(coverImage);
                }
            }
        });

        searchResultList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (searchResultList.getSelectionModel().getSelectedItem() == null) return;
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
