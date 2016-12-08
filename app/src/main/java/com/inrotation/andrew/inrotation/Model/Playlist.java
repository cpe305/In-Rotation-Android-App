package com.inrotation.andrew.inrotation.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Playlist {

    public String playlistName;
    public String owner;
    private int currentSongIndex;
    private String key;

    public Playlist() {
        //Empty Constructor becauase of Firebase requirement
    }

    public Playlist(String playlistName, String owner) {
        this.playlistName = playlistName;
        this.owner = owner;
        this.currentSongIndex = 0;

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

}