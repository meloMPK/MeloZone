package com.melompk.melo;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DownloadUtils {
    public static void DownloadSong(String id){
        id+=".mp3";
        if(!IsSongDownloaded(id)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", id));
            Path res = Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/" + id);
            blob.downloadTo(res);
            return;
        }
    }
    public static void DownloadCover(String id){
        id+=".jpg";
        if(!IsCoverDownloaded(id)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", id));
            Path res = Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/" + id);
            blob.downloadTo(res);
        }
    }
    public static boolean IsSongDownloaded(String id){
        return Files.exists(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/"+ id));
    }
    public static boolean IsCoverDownloaded(String id){
        return Files.exists(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/"+ id));
    }
    public static void Clear(){
        File songsDirectory = new File("src/main/resources/Songs");
        if (songsDirectory.exists()) {
            File[] files = songsDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
        File coversDirectory = new File("src/main/resources/Covers");
        if (coversDirectory.exists()) {
            File[] files = coversDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
}
