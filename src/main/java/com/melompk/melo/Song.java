package com.melompk.melo;

public class Song implements Comparable<Song>{
    public String title;
    public String albumId;
    public String artistId;
    public String songId;
    Song(String title, String albumId, String artistId, String songId) throws NumberFormatException{
        this.title=title;
        this.albumId=albumId;
        this.artistId=artistId;
        this.songId=songId;
    }
    @Override
    public int compareTo(Song arg0) {
        return CharSequence.compare(songId, arg0.songId);
    }
}
