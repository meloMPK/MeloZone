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
    public static Path DownloadSong(String id){
        id+=".mp3";
        if(!IsSongDownloaded(id)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", id));
            Path res = Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/" + id);
            blob.downloadTo(res);
            return res;
        }
        return Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/" + id);
    }
    public static Path DownloadCover(String id){
        id+=".jpg";
        if(!IsCoverDownloaded(id)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", id));
            Path res = Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/" + id);
            blob.downloadTo(res);
            return res;
        }
        return Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/" + id);
    }
    public static boolean IsSongDownloaded(String id){
        return Files.exists(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/"+ id));
    }
    public static boolean IsCoverDownloaded(String id){
        return Files.exists(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/"+ id));
    }
}
