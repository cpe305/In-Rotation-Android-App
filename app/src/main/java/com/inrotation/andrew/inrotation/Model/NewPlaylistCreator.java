package com.inrotation.andrew.inrotation.model;


/**
 * Represents the factory method to create a playlist per user request.
 * Created by Andrew Cofano on 10/30/16.
 */

public class NewPlaylistCreator {

    private String playlistName;
    private Song firstPlaylistSong;

    /**
     * Constructor for Firebase requirement
     */
    public NewPlaylistCreator() {
        //Empty Constructor becauase of Firebase requirement
    }

    /**
     *
     * @param name Display name for the newly created playlist
     */
    public void setPlaylistName(String name) {
        this.playlistName = name;
    }

    /**
     *
     * @return Display name for the newly created playlist
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /**
     *
     * @param song First song for the newly created playlist
     */
    public void setFirstPlaylistSong(Song song) {
        this.firstPlaylistSong = song;
    }

    /**
     *
     * @return First song of the newly created playlist
     */
    public Song getFirstPlaylistSong() {
        return firstPlaylistSong;
    }

    /**
     *
     * @return The newly created playlist to be hosted by the user
     */
    public Playlist createNewPlaylist() {
        SpotifyAccess accessInstance = SpotifyAccess.getInstance();
        return new Playlist(playlistName, accessInstance.getSpotifyUser().getEmail(), firstPlaylistSong);
    }
}
