package com.melompk.melo;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadUtils {
    public static Path DownloadSong(String name){
        Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", name));
        Path res = Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Songs/"+ name);
        blob.downloadTo(res);
        return res;
    }
}
