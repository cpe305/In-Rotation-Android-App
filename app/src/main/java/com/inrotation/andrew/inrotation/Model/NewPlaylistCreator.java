package com.inrotation.andrew.inrotation.model;

import com.inrotation.andrew.inrotation.presenter.NewPlaylistActivity;

import java.util.ArrayList;

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
        Playlist returnPlaylist = new Playlist(playlistName, accessInstance.getSpotifyUser().getEmail(), firstPlaylistSong);
        ArrayList<String> albumCovers = new ArrayList<>();
        albumCovers.add("https://i.scdn.co/image/25c94ed54d2cd65ff9ad182f6a72c62f5e657fbe");
        albumCovers.add("https://i.scdn.co/image/6a18417aa31ba778a28bc0edc48addbf87a7dd9f");
        albumCovers.add("https://i.scdn.co/image/627825639a712adac59443465bd8bc6400238060");
        returnPlaylist.getSongArrayList().add(new Song("Night Riders", "Major Lazer", "Peace Is The Mission", 3000, albumCovers, "spotify:track:2SKwCzTG3flzBqYQhGWj8J", true));
        return returnPlaylist;
    }
}
