package com.melompk.model;

import com.melompk.controllers.*;
import com.melompk.data.Album;
import com.melompk.data.Artist;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class EventHandlers {
    static LinkedList<CoverController> coverControllers = new LinkedList<>();
    public static LinkedList<AlbumViewController> albumViewControllers = new LinkedList<>();
    public static LinkedList<ArtistViewController> artistViewControllers = new LinkedList<>();
    public static LinkedList<PlayerController> playerControllers = new LinkedList<>();
    public static LinkedList<MainController> mainControllers = new LinkedList<>();

    public static void AddCoverController(CoverController contr) {
        coverControllers.add(contr);
    }
    public static void AddPlayerController(PlayerController contr) {
        playerControllers.add(contr);
    }
    public static void AddAlbumViewController(AlbumViewController contr) {
        albumViewControllers.add(contr);
    }
    public static void AddArtistViewController(ArtistViewController contr) {
        artistViewControllers.add(contr);
    }
    private EventHandlers(){};
    public static void SetAlbumView(boolean isReferencedFromArtist){
        for (MainController cur: mainControllers){
            cur.SetAlbumView();
        }
        for(AlbumViewController cur: albumViewControllers){
            cur.isReferencedFromArtist=isReferencedFromArtist;
        }
    }
    public static void RefreshAlbumView(Album album){
        for(AlbumViewController cur: albumViewControllers){
            cur.refresh(album);
        }
    }
    public static void RefreshArtistView(Artist artist){
        for(ArtistViewController cur: artistViewControllers){
            cur.refresh(artist);
        }
    }
    public static void SetArtistView(){
        for (MainController cur: mainControllers){
            cur.SetArtistView();
        }
    }
    public static void SetCoverTitleView(){
        for (MainController cur: mainControllers){
            cur.SetCoverTitleView();
        }
    }
    public static EventHandler<ActionEvent> RefreshCover = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                CoverImageUtils.refresh();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (CoverController contr : coverControllers) {
                contr.refresh();
            }
        }
    };
    public static EventHandler<ActionEvent> Play = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if(!SongUtils.isPlaying) {
                try {
                    SongUtils.Play();
                } catch (ExecutionException | IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                RefreshCover.handle(new ActionEvent());
                SongUtils.player.setOnEndOfMedia(() -> {
                    try {
                        SongUtils.Play();
                    } catch (ExecutionException | IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            else SongUtils.Pause();
            for(PlayerController contr: playerControllers){
                contr.refresh();
            }
        }
    };
    public static EventHandler<ActionEvent> Next = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            SongUtils.Pause();
            try {
                SongUtils.NextSong();
            } catch (ExecutionException | InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
            RefreshCover.handle(new ActionEvent());
            for (PlayerController contr : playerControllers) {
                contr.refresh();
                contr.beginTimer();
            }
            Play.handle(new ActionEvent());
        }
    };
    public static EventHandler<ActionEvent> Prev = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            SongUtils.Pause();
            try {
                SongUtils.PrevSong();
            } catch (ExecutionException | InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
            RefreshCover.handle(new ActionEvent());
            for (PlayerController contr : playerControllers) {
                contr.refresh();
            }
            Play.handle(new ActionEvent());
        }
    };
}
