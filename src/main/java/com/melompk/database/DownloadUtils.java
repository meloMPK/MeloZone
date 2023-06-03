package com.melompk.database;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class DownloadUtils {//Model
    static LinkedList<String> DownloadedSongs = new LinkedList<>();
    static LinkedList<String> DownloadedCovers = new LinkedList<>();

    public static void DownloadSong(String id) throws IOException {
        String filename = id+".mp3";
        if(!IsSongDownloaded(filename)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", filename));
            File songsDirectory = Files.createDirectories(Paths.get("src/main/resources/Songs/")).toFile();
            Path res = Paths.get(songsDirectory +"/"+ filename);
            blob.downloadTo(res);
            DownloadedSongs.add(filename);
        }
    }
    public static void DownloadCover(String id) throws IOException {
        String filename = id+".jpg";
        if(!IsCoverDownloaded(filename)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", filename));
            File coversDirectory = Files.createDirectories(Paths.get("src/main/resources/Covers")).toFile();
            Path res = Paths.get(coversDirectory +"/"+ filename);
            blob.downloadTo(res);
            DownloadedCovers.add(filename);
        }
    }
    public static boolean IsSongDownloaded(String filename){
        // return Files.exists(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/"+ filename));
        return DownloadedSongs.contains(filename);
    }
    public static boolean IsCoverDownloaded(String filename){
        // return Files.exists(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/"+ filename));
        return DownloadedCovers.contains(filename);
    }
    public static void Clear() throws IOException {
        // File songsDirectory = Files.createDirectories(Paths.get("src/main/resources/Songs")).toFile();
        // if (songsDirectory.exists()) {
        //     File[] files = songsDirectory.listFiles();
        //     if (files != null) {
        //         for (File file : files) {
        //             file.delete();
        //         }
        //     }
        // }
        while(!DownloadedSongs.isEmpty()) {
            Paths.get("src/main/resources/Songs/" + DownloadedSongs.getLast()).toFile().delete();
            DownloadedSongs.removeLast();
        }
        // File coversDirectory = Files.createDirectories(Paths.get("src/main/resources/Covers")).toFile();
        // File[] files = coversDirectory.listFiles();
        // if (files != null) {
        //     for (File file : files) {
        //         file.delete();
        //     }
        // }
        while(!DownloadedCovers.isEmpty()) {
            Paths.get("src/main/resources/Covers/" + DownloadedCovers.getLast()).toFile().delete();
            DownloadedCovers.removeLast();
        }
    }
}
