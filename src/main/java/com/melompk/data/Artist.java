package com.melompk.data;

public class Artist extends MediaInfo {
    public Artist(String name, String artistId){
        this.name=name;
        this.artistId=artistId;
        this.imageId=artistId;
    }
    @Override
    public int compareTo(MediaInfo other) {
        if(!(other instanceof Artist)) return 0;
        return CharSequence.compare(artistId, ((Artist) other).artistId);
    }
}
