package com.melompk.data;

public class Song implements MediaInfo {
    public String title;
    public String albumId;
    public String artistId;
    public String songId;
    public Song(String title, String albumId, String artistId, String songId) throws NumberFormatException{
        this.title=title;
        this.albumId=albumId;
        this.artistId=artistId;
        this.songId=songId;
    }
    @Override
    public int compareTo(MediaInfo other) {
        if(!(other instanceof Song)) return 0;
        return CharSequence.compare(songId, ((Song) other).songId);
    }
}
