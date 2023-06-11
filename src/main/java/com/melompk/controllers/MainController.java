package com.melompk.controllers;

import com.melompk.database.DownloadUtils;
import com.melompk.model.EventHandlers;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public AnchorPane pane, titlebar;
    public AnchorPane artistView;
    public AnchorPane albumView;
    public AnchorPane coverTitleView;
    private static Stage stage;
    private static Scene scene;
    public Button closeButton;
    public Button minimiseButton;

    public void SetAlbumView(){
        albumView.setVisible(true);
        artistView.setVisible(false);
        coverTitleView.setVisible(false);
    }
    public void SetArtistView(){
        albumView.setVisible(false);
        artistView.setVisible(true);
        coverTitleView.setVisible(false);
    }
    public void SetCoverTitleView(){
        albumView.setVisible(false);
        artistView.setVisible(false);
        coverTitleView.setVisible(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetCoverTitleView();
        EventHandlers.mainControllers.add(this);

        final Delta dragDelta = new Delta();
        titlebar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                dragDelta.x = stage.getX() - mouseEvent.getScreenX();
                dragDelta.y = stage.getY() - mouseEvent.getScreenY();
            }
        });

        titlebar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                stage.setY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });


    }

    public void closeApp(ActionEvent actionEvent) {
        Platform.exit();
        try {
            DownloadUtils.Clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

    public void minimiseApp(ActionEvent actionEvent) {
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).setIconified(true);
    }

    class Delta { double x, y; }
}
