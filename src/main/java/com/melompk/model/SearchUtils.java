package com.melompk.model;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.melompk.data.Album;
import com.melompk.data.Artist;
import com.melompk.data.MediaInfo;
import com.melompk.database.FirebaseHandler;
import com.melompk.data.Song;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class SearchUtils {
    private SearchUtils(){};
    public static LinkedList<MediaInfo> SearchAll(String query) throws ExecutionException, InterruptedException {
        LinkedList<MediaInfo> media = new LinkedList<>();
        media.addAll(SearchArtists(query));
        media.addAll(SearchSongs(query));
        media.addAll(SearchAlbums(query));
        return media;
    }
    public static LinkedList<MediaInfo> SearchArtists(String query) throws ExecutionException, InterruptedException {
        LinkedList<MediaInfo> artists = new LinkedList<>();
        for(QueryDocumentSnapshot artist: FirebaseHandler.db.collection("Artists").orderBy("Name").startAt(query).endAt(query+"\uf8ff").limit(3).get().get().getDocuments()){
            artists.add(new Artist((String) artist.get("Name"), artist.getId()));
        }
        return artists;
    }
    public static LinkedList<MediaInfo> SearchSongs(String query) throws ExecutionException, InterruptedException, NumberFormatException {
        LinkedList<MediaInfo> songs = new LinkedList<>();
        for(QueryDocumentSnapshot song:FirebaseHandler.db.collection("Songs").orderBy("Name").startAt(query).endAt(query+"\uf8ff").limit(10).get().get().getDocuments()){
            String artistName="";
            if(song.get("ArtistId")!=null){
                artistName = (String) FirebaseHandler.db.collection("Artists").document((String) song.get("ArtistId")).get().get().get("Name");
            }
            songs.add(new Song((String) song.get("Name"), (String) song.get("AlbumId"), (String) song.get("ArtistId"),  song.getId(),artistName));
        }
        return songs;
    }
    public static LinkedList<MediaInfo> SearchAlbums(String query) throws ExecutionException, InterruptedException, NumberFormatException {
        LinkedList<MediaInfo> albums = new LinkedList<>();
        for(QueryDocumentSnapshot album:FirebaseHandler.db.collection("Albums").orderBy("Name").startAt(query).endAt(query+"\uf8ff").limit(5).get().get().getDocuments()){
            albums.add(new Album((String) album.get("Name"), (String) album.get("ArtistId"), album.getId()));
        }
        return albums;
    }
}
