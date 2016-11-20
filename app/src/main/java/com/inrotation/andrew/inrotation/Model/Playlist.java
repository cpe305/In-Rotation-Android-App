package com.inrotation.andrew.inrotation.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Playlist {

    public String playlistName;
    public HostUser owner;
    public int songCount;
    public int playlistDuration;

    private PlaylistSongNode currentSong;
    private Song firstSong;
    private PlaylistSongNode songListHead;


    public Playlist(String playlistName, HostUser owner, Song firstSong) {
        this.playlistName = playlistName;
        this.owner = owner;
        this.firstSong = firstSong;

        songListHead = new PlaylistSongNode(this.firstSong);
        currentSong = songListHead;

    }











}