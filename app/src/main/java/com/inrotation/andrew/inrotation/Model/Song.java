package com.inrotation.andrew.inrotation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Song implements Serializable {

    public String songName;
    public  String artists;
    public String album;
    public int duration;
    public List<String> albumCoverURLs;
    public String songURI;
    public boolean isExplicit;

    public Song() {
        //Empty Constructor becauase of Firebase requirement
    }

    public Song(String songName, String artists, String album, int duration, List<String> albumCoverURLs, String songURI, boolean isExplicit) {
        this.songName = songName;
        this.artists = artists;
        this.album = album;
        this.duration = duration;
        this.albumCoverURLs = albumCoverURLs;
        this.songURI = songURI;
        this.isExplicit = isExplicit;
    }

}