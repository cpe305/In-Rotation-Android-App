package com.inrotation.andrew.inrotation.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Playlist {

    public String playlistName;
    public String owner;
    //public int songCount;
    //public int playlistDuration;

    private int firstSongIndex;
    private ArrayList<Song> songArrayList;
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
        this.firstSongIndex = 0;
        songArrayList.add(firstSong);

    }

    public int getFirstSongIndex() {
        return firstSongIndex;
    }

    public void setFirstSongIndex(int firstSongIndex) {
        this.firstSongIndex = firstSongIndex;
    }

    public ArrayList<Song> getSongArrayList() {
        return songArrayList;
    }

    public void setSongArrayList(ArrayList<Song> songArrayList) {
        this.songArrayList = songArrayList;
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