package com.melompk.melo;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DownloadUtils {//Model
    public static void DownloadSong(String id) throws IOException {
        id+=".mp3";
        if(!IsSongDownloaded(id)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", id));
            File songsDirectory = Files.createDirectories(Paths.get("src/main/resources/Songs/")).toFile();
            Path res = Paths.get(songsDirectory +"/"+ id);
            blob.downloadTo(res);
        }
    }
    public static void DownloadCover(String id) throws IOException {
        id+=".jpg";
        if(!IsCoverDownloaded(id)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", id));
            File coversDirectory = Files.createDirectories(Paths.get("src/main/resources/Covers")).toFile();
            Path res = Paths.get(coversDirectory +"/"+ id);
            blob.downloadTo(res);
        }
    }
    public static boolean IsSongDownloaded(String id){
        return Files.exists(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/"+ id));
    }
    public static boolean IsCoverDownloaded(String id){
        return Files.exists(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/"+ id));
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
