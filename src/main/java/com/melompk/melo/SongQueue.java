package com.melompk.melo;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

public class SongQueue {//Model
    static ConcurrentLinkedDeque<Song> que = new ConcurrentLinkedDeque<>();
    static LinkedList<Song> playHistory = new LinkedList<>();
    // static Song curSong = null;
    public static void Add(Song song){
        que.add(song);
    }
    public static void AddAll(LinkedList<Song> songs){
        que.addAll(songs);
    }

    public static Song NextSong() throws ExecutionException, InterruptedException, IOException {
        // if(que.isEmpty() && playHistory.isEmpty()){
        //     AddAll(GetData.GetAllSongs());
        // }
        if(que.isEmpty()){
            // curSong=null;
            return null;
        }
        if(SongUtils.curSong!=null)playHistory.addFirst(SongUtils.curSong);
        Song out = que.poll();
        DownloadUtils.DownloadSong(out.songId);
        DownloadUtils.DownloadCover(out.albumCoverID);
        return out;
    }

    public static Song PrevSong() throws ExecutionException, InterruptedException, IOException {
        if (playHistory.isEmpty()) return SongUtils.curSong;
        if(SongUtils.curSong!=null){
            que.addFirst(SongUtils.curSong);
        }
        Song out = playHistory.getFirst();
        playHistory.removeFirst();
        DownloadUtils.DownloadSong(out.songId);
        DownloadUtils.DownloadCover(out.albumCoverID);
        return out;
    }
}
