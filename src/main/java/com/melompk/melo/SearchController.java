package com.melompk.melo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> searchResultList;

    @FXML
    private TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    static class SearchCell extends ListCell<String> {
        HBox hbox = new HBox();
        Button btn = new Button("test");
        Pane pane = new Pane();
        Label label = new Label("");
        Image coverImg = new Image("/src/main/resources/Utilities/default.jpg");
        ImageView img = new ImageView(coverImg);

        public SearchCell() {
            super();

            hbox.getChildren().addAll(img, label, pane, btn);
            hbox.setHgrow(pane, Priority.ALWAYS);
        }

        public void updateItem(String name, boolean empty) {
            super.updateItem(name, empty);
            setText(null);
            setGraphic(null);

            if (name != null && !empty) {
                label.setText(name);
                setGraphic(hbox);
            }

            
        }
    }
    public void getSearchQuery() {

    }

    public void search() {

    }
}
