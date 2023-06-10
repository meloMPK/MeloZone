package com.melompk.data;

public class Song extends MediaInfo {
    public String albumId;
    public String songId;
    public Long albumPosition;
    public Song(String title, String albumId, String artistId, String songId, String artistName, Long albumPosition) throws NumberFormatException{
        this.name=title;
        this.albumId=albumId;
        this.artistId=artistId;
        this.songId=songId;
        this.artistName=artistName;
        this.imageId=albumId;
        this.albumPosition=albumPosition;
    }
    @Override
    public int compareTo(MediaInfo other) {
        if(!(other instanceof Song)) return 0;
        return CharSequence.compare(songId, ((Song) other).songId);
    }
}
