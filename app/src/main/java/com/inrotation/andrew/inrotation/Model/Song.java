package com.inrotation.andrew.inrotation.Model;

import java.util.ArrayList;

public class Song {

    public String songName;
    private ArrayList<String> artists;
    private String album;
    private int duration;
    private ArrayList<String> albumCoverURLs;
    private String songURI;



    public Song(String songName, ArrayList<String> artists, String album, int duration, ArrayList<String> albumCoverURLs, String songURI) {
        this.songName = songName;
        this.artists = artists;
        this.album = album;
        this.duration = duration;
        this.albumCoverURLs = albumCoverURLs;
        this.songURI = songURI;
    }









}