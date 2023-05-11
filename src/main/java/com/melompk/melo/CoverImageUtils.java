package com.melompk.melo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CoverImageUtils {
    static Image image;
    static String albumCoverID;
    static ImageView imageView;

    public static void init(ImageView imv) throws IOException {
        imageView = imv;
        refresh();
    }

    public static void refresh() throws IOException {
        if(SongUtils.curSong!=null){
            albumCoverID = SongUtils.curSong.albumCoverID;
            DownloadUtils.DownloadCover(albumCoverID);
            if(DownloadUtils.IsCoverDownloaded(albumCoverID+".jpg")) {
                image = new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/"+ albumCoverID + ".jpg").toUri().toString());
            }
            else {
                image = new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/default.jpg").toUri().toString());
            }
        }
        else {
            image = new Image(Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/default.jpg").toUri().toString());
        }
        imageView.setImage(image);
        imageView.setFitHeight(225);
        imageView.setFitWidth(225);
    }
}
