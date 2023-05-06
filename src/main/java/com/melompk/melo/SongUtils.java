package com.melompk.melo;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SongUtils {
    static Media curSong;
    static MediaPlayer player=null;
    public static void Play(Song song){
        Pause();
        curSong = new Media(GetPath(song.songId));
        player = new MediaPlayer(curSong);
        player.play();
        player.setOnEndOfMedia(()->{
            player.pause();
            SongQueue.Play();
        });
    }
    public static void Play(Path path) {
        Pause();
        curSong = new Media(path.toUri().toString());
        player = new MediaPlayer(curSong);
        player.play();
    }
    public static void Pause() {
        if(player!=null){
            player.pause();
        }
    }
    public static String GetPath(String id){
        return Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/" + id + ".mp3").toUri().toString();
    }
    public static void Clear(){
        File songsDirectory = new File("src/main/resources/Songs");
        if (songsDirectory.exists()) {
            File[] files = songsDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
        File coversDirectory = new File("src/main/resources/Covers");
        if (coversDirectory.exists()) {
            File[] files = coversDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
}

