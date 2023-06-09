package com.melompk.database;

import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.melompk.data.Album;
import com.melompk.data.Song;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class GetData {//Model

    private GetData() {
    }

    public static LinkedList<Song> GetAllSongs() throws ExecutionException, InterruptedException, NumberFormatException {
        LinkedList<Song> songs = new LinkedList<>();
        for (QueryDocumentSnapshot song : FirebaseHandler.db.collection("Songs").get().get().getDocuments()) {
            String artistName = "";
            if (song.get("ArtistId") != null) {
                artistName = (String) FirebaseHandler.db.collection("Artists").document((String) song.get("ArtistId")).get().get().get("Name");
            }
            songs.add(new Song((String) song.get("Name"), (String) song.get("AlbumId"), (String) song.get("ArtistId"), song.getId(), artistName, (Long) song.get("Position")));

        }
        return songs;
    }

    public static LinkedList<Song> GetAllSongsFromArtist(String artistId) throws ExecutionException, InterruptedException {
        LinkedList<Song> songs = new LinkedList<>();
        for (QueryDocumentSnapshot song : FirebaseHandler.db.collection("Songs").whereEqualTo("ArtistId", artistId).get().get()) {
            String artistName = "";
            if (song.get("ArtistId") != null) {
                artistName = (String) FirebaseHandler.db.collection("Artists").document((String) song.get("ArtistId")).get().get().get("Name");
            }
            songs.add(new Song((String) song.get("Name"), (String) song.get("AlbumId"), (String) song.get("ArtistId"), song.getId(), artistName, (Long) song.get("Position")));
        }
        return songs;
    }

    public static LinkedList<Song> GetAllSongsFromAlbum(String albumId) throws ExecutionException, InterruptedException {
        LinkedList<Song> songs = new LinkedList<>();
        for (QueryDocumentSnapshot song : FirebaseHandler.db.collection("Songs").orderBy("Position", Query.Direction.ASCENDING).whereEqualTo("AlbumId", albumId).get().get()) {
            String artistName = "";
            if (song.get("ArtistId") != null) {
                artistName = (String) FirebaseHandler.db.collection("Artists").document((String) song.get("ArtistId")).get().get().get("Name");
            }
            songs.add(new Song((String) song.get("Name"), (String) song.get("AlbumId"), (String) song.get("ArtistId"), song.getId(), artistName, (Long) song.get("Position")));
        }
        return songs;
    }

    public static LinkedList<Album> GetAllAlbumsFromArtist(String artistId) throws ExecutionException, InterruptedException {
        LinkedList<Album> albums = new LinkedList<>();
        for (QueryDocumentSnapshot album : FirebaseHandler.db.collection("Albums").whereEqualTo("ArtistId", artistId).get().get()) {
            String artistName = "";
            if (album.get("ArtistId") != null) {
                artistName = (String) FirebaseHandler.db.collection("Artists").document((String) album.get("ArtistId")).get().get().get("Name");
            }
            albums.add(new Album((String) album.get("Name"), artistId, album.getId(), artistName));
        }
        return albums;
    }
}