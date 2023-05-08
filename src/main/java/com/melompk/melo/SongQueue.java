package com.melompk.melo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class SongQueue {//Model
    static LinkedList<Song> que = new LinkedList<>();
    static Song curSong = null;
    public static void Add(Song song){
        que.add(song);
    }
    public static void AddAll(LinkedList<Song> songs){
        que.addAll(songs);
    }
    public static Song NextSong() throws ExecutionException, InterruptedException {
        if(que.isEmpty()){
            AddAll(GetData.GetAllSongs());
        }
        curSong = que.removeFirst();
        DownloadUtils.DownloadSong(curSong.songId);
        DownloadUtils.DownloadCover(curSong.albumCoverID);
        return curSong;
    }
}
