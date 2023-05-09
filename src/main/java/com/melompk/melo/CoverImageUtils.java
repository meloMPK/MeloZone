package com.melompk.melo;

import java.io.File;
import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CoverImageUtils {
    static Image image;
    static String albumCoverID;
    static ImageView imageView;

    public static void init(ImageView imv) {
        // String path = Paths.get(new File("").getAbsolutePath() + "/src/main/resources/Covers/cover.jpg").toUri().toString();
        String path = Paths.get(new File("").getAbsolutePath() + "/cover.jpg").toUri().toString();

        image = new Image(path);
        imageView = imv;
        imv.setImage(image);
    }
}
