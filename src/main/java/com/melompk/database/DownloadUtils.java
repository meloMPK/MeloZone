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
    static LinkedList<String> DownloadedArtistImages = new LinkedList<>();
    private DownloadUtils() {
    }

    public static void DownloadSong(String id) throws IOException {
        String filename = id + ".mp3";
        if (!IsSongDownloaded(filename)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", filename));
            File songsDirectory = Files.createDirectories(Paths.get("src/main/resources/Songs/")).toFile();
            Path res = Paths.get(songsDirectory + "/" + filename);
            blob.downloadTo(res);
            DownloadedSongs.add(filename);
        }
    }

    public static void DownloadCover(String id) throws IOException {
        String filename = id + ".jpg";
        if (!IsCoverDownloaded(filename)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", filename));
            File coversDirectory = Files.createDirectories(Paths.get("src/main/resources/Covers")).toFile();
            Path res = Paths.get(coversDirectory + "/" + filename);
            if (blob != null) {
                blob.downloadTo(res);
                DownloadedCovers.add(filename);
            }
        }
    }

    public static void DownloadArtistImage(String id) throws IOException {
        String filename = id + ".jpg";
        if (!IsArtistImageDownloaded(filename)) {
            Blob blob = FirebaseHandler.storage.get(BlobId.of("melozone-7db34.appspot.com", "ArtistImages/" + filename));
            File artistImagesDirectory = Files.createDirectories(Paths.get("src/main/resources/ArtistImages")).toFile();
            Path res = Paths.get(artistImagesDirectory + "/" + filename);
            if (blob != null) {
                blob.downloadTo(res);
                DownloadedArtistImages.add(filename);
            }
        }
    }

    public static boolean IsSongDownloaded(String filename) {
        return DownloadedSongs.contains(filename);
    }

    public static boolean IsCoverDownloaded(String filename) {
        return DownloadedCovers.contains(filename);
    }

    public static boolean IsArtistImageDownloaded(String filename) {
        return DownloadedArtistImages.contains(filename);
    }

    public static void Clear() throws IOException {

        while (!DownloadedSongs.isEmpty()) {
            Paths.get("src/main/resources/Songs/" + DownloadedSongs.getLast()).toFile().delete();
            DownloadedSongs.removeLast();
        }
        while (!DownloadedCovers.isEmpty()) {
            Paths.get("src/main/resources/Covers/" + DownloadedCovers.getLast()).toFile().delete();
            DownloadedCovers.removeLast();
        }
    }
}
