package com.melompk.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.melompk.database.DownloadUtils;
import javafx.scene.image.Image;

public class CoverImageUtils {
    public static Image image;
    static String albumCoverID;

    public static void init() throws IOException {
        refresh();
    }

    public static void refresh() throws IOException {
        if(SongUtils.curSong!=null){
            albumCoverID = SongUtils.curSong.albumId;
            DownloadUtils.DownloadCover(albumCoverID);
            if(DownloadUtils.IsCoverDownloaded(albumCoverID+".jpg")) {
                image = new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/"+ albumCoverID + ".jpg").toUri().toString());
            }
            else {
                image = new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString());
            }
        }
        else {
            image = new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Utilities/default.jpg").toUri().toString());
        }
    }
}
