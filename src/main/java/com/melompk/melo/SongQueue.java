package com.melompk.melo;

import java.util.Iterator;
import java.util.LinkedList;

public class SongQueue {
    static LinkedList<Song> que = new LinkedList<>();
    static Song curSong = null;
    public static void Add(Song song){
        que.add(song);
    }
    public static void AddAll(LinkedList<Song> songs){
        que.addAll(songs);
    }
    public static void Play(){
        if(!que.isEmpty()) {
            curSong = que.removeFirst();
            DownloadUtils.DownloadSong(curSong.songId);
            DownloadUtils.DownloadCover(curSong.albumCoverID);
            SongUtils.Play(curSong);
        }
    }
}
