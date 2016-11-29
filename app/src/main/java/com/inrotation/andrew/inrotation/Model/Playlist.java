package com.inrotation.andrew.inrotation.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Playlist {

    public String playlistName;
    public String owner;
    //public int songCount;
    //public int playlistDuration;

    private int currentSongIndex;
    private ArrayList<Song> songArrayList;
    private String key;
    /*private PlaylistSongNode currentSong;
    private Song firstSong;
    private PlaylistSongNode songListHead;*/

    public Playlist() {
        //
    }

    public Playlist(String playlistName, String owner, Song firstSong) {
        this.playlistName = playlistName;
        this.owner = owner;
        this.songArrayList = new ArrayList<>();
        this.currentSongIndex = 0;
        songArrayList.add(firstSong);

    }

    public int getCurrentSongIndex() {
        return currentSongIndex;
    }

    public void setCurrentSongIndex(int currentSongIndex) {
        this.currentSongIndex = currentSongIndex;
    }

    public ArrayList<Song> getSongArrayList() {
        return songArrayList;
    }

    public void setSongArrayList(ArrayList<Song> songArrayList) {
        this.songArrayList = songArrayList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    /*
    public PlaylistSongNode getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(PlaylistSongNode currentSong) {
        this.currentSong = currentSong;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public Song getFirstSong() {
        return firstSong;
    }

    public String getOwner() {
        return owner;
    }


    public PlaylistSongNode getSongListHead() {
        return songListHead;
    }*/



}