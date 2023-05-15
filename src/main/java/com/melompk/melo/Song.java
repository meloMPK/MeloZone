package com.melompk.melo;

public class Song {
    public String title;
    public String album;
    public String artist;
    public String songId;
    public String albumCoverID;
    public int intID;
    Song(String title, String album, String artist, String songId, String albumCoverId) throws NumberFormatException{
        this.title=title;
        this.album=album;
        this.artist=artist;
        this.songId=songId;
        this.albumCoverID=albumCoverId;
        this.intID = Integer.parseInt(songId);
    }
}
