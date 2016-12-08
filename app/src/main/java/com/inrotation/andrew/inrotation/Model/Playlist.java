package com.inrotation.andrew.inrotation.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Represents a playlist being hosted by a HostUser.
 * Created by Andrew Cofano
 */
public class Playlist {

    public String playlistName;
    public String owner;
    private int currentSongIndex;
    private String key;
    private Song firstSong;

    public Playlist() {
        //Empty Constructor becauase of Firebase requirement
    }

    public Playlist(String playlistName, String owner, Song firstSong) {
        this.playlistName = playlistName;
        this.owner = owner;
        this.currentSongIndex = 0;
        this.firstSong = firstSong;

    }

    public int getCurrentSongIndex() {
        return currentSongIndex;
    }

    public void setCurrentSongIndex(int currentSongIndex) {
        this.currentSongIndex = currentSongIndex;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Song getFirstSong() {
        return firstSong;
    }

    public void setFirstSong(Song firstSong) {
        this.firstSong = firstSong;
    }
}