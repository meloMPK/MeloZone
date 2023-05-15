package com.melompk.melo;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class GetData {//Model
    public static String FindSongId(String artist, String album, String title) throws ExecutionException, InterruptedException {
        return(String) FirebaseHandler.db.collection("Songs")
                .whereEqualTo("Artist", artist)
                .whereEqualTo("Album", album)
                .whereEqualTo("Name", title).get().get().getDocuments().get(0).getData().get("dbId");
    }
    public static Song GetSong(String artist, String album, String title) throws ExecutionException, InterruptedException, NumberFormatException {
        String songId = (String) FirebaseHandler.db.collection("Songs")
                .whereEqualTo("Artist", artist)
                .whereEqualTo("Album", album)
                .whereEqualTo("Name", title).get().get().getDocuments().get(0).getData().get("dbId");
        String albumId = (String) FirebaseHandler.db.collection("Albums")
                .whereEqualTo("Artist", artist)
                .whereEqualTo("Name", album).get().get().getDocuments().get(0).getData().get("dbId");
        return new Song(title,album,artist, songId, albumId);
    }
    public static LinkedList<Song> GetAllSongs() throws ExecutionException, InterruptedException, NumberFormatException {
        LinkedList<Song> songs = new LinkedList<>();
        for(QueryDocumentSnapshot song: FirebaseHandler.db.collection("Songs").get().get().getDocuments()){
            String albumId = (String) FirebaseHandler.db.collection("Albums")
                    .whereEqualTo("dbId", song.get("AlbumId")).get().get().getDocuments().get(0).getData().get("dbId");
            songs.add(new Song((String) song.get("Name"),
                    (String) song.get("Album"), (String) song.get("Artist"), (String) song.get("dbId"), albumId));
        }
        return songs;
    }
}