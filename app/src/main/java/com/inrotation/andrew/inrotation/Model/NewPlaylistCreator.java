package com.inrotation.andrew.inrotation.model;

import com.inrotation.andrew.inrotation.presenter.NewPlaylistActivity;

/**
 * Created by Andrew on 10/30/16.
 */

public class NewPlaylistCreator {

    private String playlistName;
    private Song firstPlaylistSong;

    public NewPlaylistCreator() {

    }

    public void setPlaylistName(String name) {
        this.playlistName = name;
    }

    public void setFirstPlaylistSong(Song song) {
        this.firstPlaylistSong = song;
    }

    public Playlist createNewPlaylist() {
        SpotifyAccess accessInstance = SpotifyAccess.getInstance();
        return new Playlist(playlistName, accessInstance.getSpotifyUser(), firstPlaylistSong);
    }
}
