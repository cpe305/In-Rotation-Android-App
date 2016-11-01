package com.inrotation.andrew.inrotation.Model;

import java.util.ArrayList;

public class Song {

    public String songName;
    public  String artists;
    public String album;
    public int duration;
    public ArrayList<String> albumCoverURLs;
    public String songURI;
    public boolean isExplicit;



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