package com.inrotation.andrew.inrotation.Model;

import java.util.ArrayList;

public class Playlist {

    private String playlistName;
    private HostUser owner;
    private int songCount;
    private int playlistDuration;

    private Song currentSong;
    private ArrayList<Song> songList;
    private Song nextSong;
    private Song prevSong;

    public Playlist(String playlistName, HostUser owner, int songCount, int playlistDuration) {
        this.playlistName = playlistName;
        this.owner = owner;
        this.songCount = songCount;
        this.playlistDuration = playlistDuration;
    }






}