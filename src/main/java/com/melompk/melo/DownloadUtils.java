package com.melompk.melo;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DownloadUtils {
    public static Path DownloadSong(String id){
        Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", id));
        Path res = Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/"+ id);
        blob.downloadTo(res);
        return res;
    }
}
