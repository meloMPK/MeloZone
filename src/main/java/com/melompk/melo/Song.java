package com.melompk.melo;

public class Song {
    public String title;
    public String album;
    public String artist;
    public String songId;
    public String albumCoverID;
    Song(String title, String album, String artist, String songId, String albumCoverId){
        this.title=title;
        this.album=album;
        this.artist=artist;
        this.songId=songId;
        this.albumCoverID=albumCoverId;
    }

    // TODO: Write comparator pls
}
