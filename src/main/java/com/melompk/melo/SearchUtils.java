package com.melompk.melo;

import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class SearchUtils {
    public static LinkedList<Song> SearchSongsByTitle(String query) throws ExecutionException, InterruptedException, NumberFormatException {
        LinkedList<Song> songs = new LinkedList<>();
        for(QueryDocumentSnapshot song:FirebaseHandler.db.collection("Songs").orderBy("Name").startAt(query).endAt(query+"\uf8ff").limit(10).get().get().getDocuments()){
            String albumId = (String) FirebaseHandler.db.collection("Albums")
                    .whereEqualTo("dbId", song.get("AlbumId")).get().get().getDocuments().get(0).getData().get("dbId");
            songs.add(new Song((String) song.get("Name"),
                    (String) song.get("Album"), (String) song.get("Artist"), (String) song.get("dbId"), albumId));
        }
        return songs;
    }
    public static LinkedList<Album> SearchAlbumsByTitle(String query) throws ExecutionException, InterruptedException, NumberFormatException {
        LinkedList<Album> albums = new LinkedList<>();
        for(QueryDocumentSnapshot album:FirebaseHandler.db.collection("Albums").orderBy("Name").startAt(query).endAt(query+"\uf8ff").limit(10).get().get().getDocuments()){
            albums.add(new Album((String) album.get("Name"), (String) album.get("Artist"), (String) album.get("dbId")));
        }
        return albums;
    }
    public static LinkedList<Album> SearchAlbumsByArtist(String query) throws ExecutionException, InterruptedException, NumberFormatException {
        LinkedList<Album> albums = new LinkedList<>();
        for(QueryDocumentSnapshot album:FirebaseHandler.db.collection("Albums").orderBy("Artist").startAt(query).endAt(query+"\uf8ff").limit(10).get().get().getDocuments()){
            albums.add(new Album((String) album.get("Name"), (String) album.get("Artist"), (String) album.get("dbId")));
        }
        return albums;
    }
    public static LinkedList<Song> SearchSongsByArtist(String query) throws ExecutionException, InterruptedException, NumberFormatException {
        LinkedList<Song> songs = new LinkedList<>();
        for(QueryDocumentSnapshot song:FirebaseHandler.db.collection("Songs").orderBy("Artist").startAt(query).endAt(query+"\uf8ff").limit(10).get().get().getDocuments()){
            String albumId = (String) FirebaseHandler.db.collection("Albums")
                    .whereEqualTo("dbId", song.get("AlbumId")).get().get().getDocuments().get(0).getData().get("dbId");
            songs.add(new Song((String) song.get("Name"),
                    (String) song.get("Album"), (String) song.get("Artist"), (String) song.get("dbId"), albumId));
        }
        return songs;
    }
}
