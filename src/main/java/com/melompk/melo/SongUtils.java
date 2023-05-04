package com.melompk.melo;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SongUtils {
    public static void Play(Path path){
        Media song = new Media(path.toUri().toString());
        MediaPlayer player = new MediaPlayer(song);
        player.play();
    }
    public static void Clear(){
        File directory = new File("src/main/resources/Songs");
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
}

