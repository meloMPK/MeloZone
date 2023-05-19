package com.melompk.melo;

public class Album implements MediaInfo{
    public String title;
    public String artistId;
    public String albumId;
    Album(String title, String artistId, String albumId){
        this.title=title;
        this.artistId=artistId;
        this.albumId=albumId;
    }
    @Override
    public int compareTo(MediaInfo other) {
        if(!(other instanceof Album)) return 0;
        return CharSequence.compare(albumId, ((Album) other).albumId);
    }
}
