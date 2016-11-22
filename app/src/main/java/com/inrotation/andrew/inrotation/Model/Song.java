package com.inrotation.andrew.inrotation.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Song implements Serializable {

    public String songName;
    public  String artists;
    public String album;
    public int duration;
    public ArrayList<String> albumCoverURLs;
    public String songURI;
    public boolean isExplicit;

    public Song() {

    }

    public Song(String songName, String artists, String album, int duration, ArrayList<String> albumCoverURLs, String songURI, boolean isExplicit) {
        this.songName = songName;
        this.artists = artists;
        this.album = album;
        this.duration = duration;
        this.albumCoverURLs = albumCoverURLs;
        this.songURI = songURI;
        this.isExplicit = isExplicit;
    }

}