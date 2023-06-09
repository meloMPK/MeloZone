package com.melompk.model;

import com.melompk.data.Song;
import com.melompk.database.DownloadUtils;
import com.melompk.database.GetData;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;

public class SongQueue {//Model

    static ConcurrentLinkedDeque<Song> que;

    static LinkedList<Song> playHistory;
    private SongQueue() {
    }

    public static void Add(Song song) {
        que.add(song);
    }

    public static void Clear() {
        que.clear();
    }

    public static void AddFront(Song song) {
        que.addFirst(song);
    }

    public static void AddBack(Song song) {
        que.addLast(song);
    }

    public static boolean IsEmpty() {
        return que.isEmpty();
    }

    public static void AddAll(LinkedList<Song> songs) {
        que.addAll(songs);
    }

    public static void Init() throws NumberFormatException, ExecutionException, InterruptedException {
        que = new ConcurrentLinkedDeque<>();
        playHistory = new LinkedList<>();
    }

    public static Song NextSong() throws ExecutionException, InterruptedException, IOException {
        if (que.isEmpty()) {
            if (playHistory.isEmpty()) {
                que.addAll(GetData.GetAllSongs());
            } else return null;
        }
        if (SongUtils.curSong != null) playHistory.addFirst(SongUtils.curSong);
        Song out = que.poll();
        DownloadUtils.DownloadSong(out.songId);
        DownloadUtils.DownloadCover(out.albumId);
        if (!que.isEmpty()) {
            DownloadUtils.DownloadSong(que.getFirst().songId);
            DownloadUtils.DownloadCover(que.getFirst().albumId);
        }
        return out;
    }

    public static Song PrevSong() throws ExecutionException, InterruptedException, IOException {
        if (playHistory.isEmpty()) return SongUtils.curSong;
        if (SongUtils.curSong != null) {
            que.addFirst(SongUtils.curSong);
        }
        Song out = playHistory.getFirst();
        playHistory.removeFirst();
        DownloadUtils.DownloadSong(out.songId);
        DownloadUtils.DownloadCover(out.albumId);
        return out;
    }
}
