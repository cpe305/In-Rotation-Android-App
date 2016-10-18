package com.inrotation.andrew.inrotation.Model;

public class Playlist {

    private String playlistName;
    private String owner;
    private int songCount;
    private int playlistDuration;

    private Song currentSong;
    private Song nextSong;
    private Song prevSong;

    public Playlist(String playlistName, String owner, int songCount, int playlistDuration) {
        this.playlistName = playlistName;
        this.owner = owner;
        this.songCount = songCount;
        this.playlistDuration = playlistDuration;
    }






}