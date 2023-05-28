package com.melompk.database;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadUtils {//Model
    public static void DownloadSong(String id) throws IOException {
        String filename = id+".mp3";
        if(!IsSongDownloaded(filename)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", filename));
            File songsDirectory = Files.createDirectories(Paths.get("src/main/resources/Songs/")).toFile();
            Path res = Paths.get(songsDirectory +"/"+ filename);
            blob.downloadTo(res);
        }
    }
    public static void DownloadCover(String id) throws IOException {
        String filename = id+".jpg";
        if(!IsCoverDownloaded(filename)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", filename));
            File coversDirectory = Files.createDirectories(Paths.get("src/main/resources/Covers")).toFile();
            Path res = Paths.get(coversDirectory +"/"+ filename);
            blob.downloadTo(res);
        }
    }
    public static boolean IsSongDownloaded(String filename){
        return Files.exists(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/"+ filename));
    }
    public static boolean IsCoverDownloaded(String filename){
        return Files.exists(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/"+ filename));
    }
    public static void Clear() throws IOException {
        File songsDirectory = Files.createDirectories(Paths.get("src/main/resources/Songs")).toFile();
        if (songsDirectory.exists()) {
            File[] files = songsDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
        File coversDirectory = Files.createDirectories(Paths.get("src/main/resources/Covers")).toFile();
        File[] files = coversDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}
