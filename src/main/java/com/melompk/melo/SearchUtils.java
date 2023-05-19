package com.melompk.melo;

import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class SearchUtils {
    public static LinkedList<Song> SearchSongsByTitle(String query) throws ExecutionException, InterruptedException, NumberFormatException {
        LinkedList<Song> songs = new LinkedList<>();
        for(QueryDocumentSnapshot song:FirebaseHandler.db.collection("Songs").orderBy("Name").startAt(query).endAt(query+"\uf8ff").limit(10).get().get().getDocuments()){
            songs.add(new Song((String) song.get("Name"), (String) song.get("AlbumId"), (String) song.get("ArtistId"),  song.getId()));
        }
        return songs;
    }
    public static LinkedList<Album> SearchAlbumsByTitle(String query) throws ExecutionException, InterruptedException, NumberFormatException {
        LinkedList<Album> albums = new LinkedList<>();
        for(QueryDocumentSnapshot album:FirebaseHandler.db.collection("Albums").orderBy("Name").startAt(query).endAt(query+"\uf8ff").limit(10).get().get().getDocuments()){
            albums.add(new Album((String) album.get("Name"), (String) album.get("ArtistId"), album.getId()));
        }
        return albums;
    }
}
