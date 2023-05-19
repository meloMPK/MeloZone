package com.melompk.melo;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class GetData {//Model
    public static LinkedList<Song> GetAllSongs() throws ExecutionException, InterruptedException, NumberFormatException {
        LinkedList<Song> songs = new LinkedList<>();
        for(QueryDocumentSnapshot song: FirebaseHandler.db.collection("Songs").get().get().getDocuments()){
            songs.add(new Song((String) song.get("Name"), (String) song.get("AlbumId"), (String) song.get("ArtistId"), song.getId()));
        }
        return songs;
    }
}