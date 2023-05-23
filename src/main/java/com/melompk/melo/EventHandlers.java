package com.melompk.melo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import static javafx.event.Event.fireEvent;

public class EventHandlers {
    static LinkedList<MainController> mainControllers = new LinkedList<>();
    static LinkedList<PlayerController> playerControllers = new LinkedList<>();

    public static void AddMainController(MainController contr) {
        mainControllers.add(contr);
    }
    public static void AddPlayerController(PlayerController contr) {
        playerControllers.add(contr);
    }

    public static EventHandler<ActionEvent> RefreshCover = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                CoverImageUtils.refresh();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (MainController contr : mainControllers) {
                contr.refresh();
            }
        }
    };
    public static EventHandler<ActionEvent> Play = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                SongUtils.Play();
            } catch (ExecutionException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            RefreshCover.handle(new ActionEvent());
            SongUtils.player.setOnEndOfMedia(()-> {
                try {
                    SongUtils.Play();
                } catch (ExecutionException | IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
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
