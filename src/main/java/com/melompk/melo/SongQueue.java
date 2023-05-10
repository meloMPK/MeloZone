package com.melompk.melo;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

public class SongQueue {//Model
    static LinkedList<Song> que = new LinkedList<>();
    static LinkedList<Song> playHistory = new LinkedList<>();
    static ListIterator<Song> queIterator = que.listIterator();
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
            resetIterator();
        }
        curSong = queIterator.hasNext() ? queIterator.next() : curSong;
        playHistory.addFirst(curSong);
        DownloadUtils.DownloadSong(curSong.songId);
        DownloadUtils.DownloadCover(curSong.albumCoverID);
        return curSong;
    }

    public static Song PrevSong() throws ExecutionException, InterruptedException {
        if (playHistory.isEmpty()) return curSong;
        else {
            curSong = playHistory.getFirst();
            playHistory.removeFirst();
            playHistory.add(curSong);
        }

        DownloadUtils.DownloadSong(curSong.songId);
        DownloadUtils.DownloadCover(curSong.albumCoverID);
        return curSong;
    }

    private static ListIterator<Song> resetIterator() {
        queIterator = que.listIterator();
        return queIterator;
    }
}
