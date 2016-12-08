package com.inrotation.andrew.inrotation.model;


/**
 * Represents the factory method to create a playlist per user request.
 * Created by Andrew Cofano on 10/30/16.
 */

public class NewPlaylistCreator {

    private String playlistName;
    private Song firstPlaylistSong;

    public NewPlaylistCreator() {
        //Empty Constructor becauase of Firebase requirement
    }

    public void setPlaylistName(String name) {
        this.playlistName = name;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setFirstPlaylistSong(Song song) {
        this.firstPlaylistSong = song;
    }

    public Song getFirstPlaylistSong() {
        return firstPlaylistSong;
    }

    public Playlist createNewPlaylist() {
        SpotifyAccess accessInstance = SpotifyAccess.getInstance();
        return new Playlist(playlistName, accessInstance.getSpotifyUser().getEmail(), firstPlaylistSong);
    }
}
