package com.melompk.melo;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class SongUtils {//Controller
    static Media curMedia;
    static MediaPlayer player=null;
    static Song curSong = null;
    public static void Play() throws ExecutionException, InterruptedException, IOException {
        //Pause();
        if(curSong!=null){
            player.play();
            return;
        }
        NextSong();
        if(curSong==null) return;
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

    public static Song getCurrentSong() {
        return curSong;
    }

    public static void NextSong() throws ExecutionException, InterruptedException, IOException {
        curSong = SongQueue.NextSong();
        if(curSong==null)return;
        curMedia = new Media(GetPath(curSong.songId));
        player = new MediaPlayer(curMedia);
        // player.play();
    }

    public static void PrevSong() throws ExecutionException, InterruptedException, IOException {
        curSong = SongQueue.PrevSong();
        if(curSong==null)return;
        curMedia = new Media(GetPath(curSong.songId));
        player = new MediaPlayer(curMedia);
        // player.play();
    }
}

