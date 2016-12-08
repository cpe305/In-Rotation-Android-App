package com.inrotation.andrew.inrotation.model;

import java.util.List;

/**
 * Represents a song from the Spotify Music Library that will make up a Playlist.
 * Created by Andrew Cofano
 */
public class Song {

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