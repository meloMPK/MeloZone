package com.melompk.melo;

public class Artist implements MediaInfo{
    String name;
    String artistId;
    Artist(String name, String artistId){
        this.name=name;
        this.artistId=artistId;
    }
    @Override
    public int compareTo(MediaInfo other) {
        if(!(other instanceof Artist)) return 0;
        return CharSequence.compare(artistId, ((Artist) other).artistId);
    }
}
