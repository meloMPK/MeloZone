package com.melompk.data;

public class Album extends MediaInfo {
    public String albumId;

    public Album(String title, String artistId, String albumId, String artistName) {
        this.name = title;
        this.artistId = artistId;
        this.albumId = albumId;
        this.imageId = albumId;
        this.artistName = artistName;
    }

    @Override
    public int compareTo(MediaInfo other) {
        if (!(other instanceof Album)) return 0;
        return CharSequence.compare(albumId, ((Album) other).albumId);
    }
}
