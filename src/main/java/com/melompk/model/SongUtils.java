package com.melompk.model;
import com.melompk.data.Song;
import com.melompk.model.SongQueue;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class SongUtils {//Controller
    private SongUtils(){};
    public static Media curMedia;
    public static MediaPlayer player=null;
    public static Song curSong = null;
    public static boolean isPlaying=false;
    public static void Play() throws ExecutionException, InterruptedException, IOException {
        //Pause();
        if(curSong!=null){
            player.play();
            isPlaying=true;
            return;
        }
        NextSong();
        if(curSong==null) return;
        isPlaying=true;
        player.play();
    }
    public static void Pause() {
        if(player!=null){
            player.pause();
        }
        isPlaying=false;
    }
    public static String GetPath(String id){
        return Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/" + id + ".mp3").toUri().toString();
    }

    public static Song getCurrentSong() {
        return curSong;
    }

    public static void NextSong() throws ExecutionException, InterruptedException, IOException {
        curSong = SongQueue.NextSong();
        if(curSong==null) return;
        curMedia = new Media(GetPath(curSong.songId));
        player = new MediaPlayer(curMedia);

        player.setOnEndOfMedia(() -> {
            System.out.println("shca;ibg");
            SongUtils.curSong = null;
            EventHandlers.Next.handle(new ActionEvent());
        });
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

