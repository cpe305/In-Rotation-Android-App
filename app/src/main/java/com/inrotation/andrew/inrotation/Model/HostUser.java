package com.inrotation.andrew.inrotation.model;


import java.util.ArrayList;

/**
 * Represents the current user who can host a playlist, search the Spotify Music Library, and play songs.
 * Created by Andrew on 10/30/16.
 */

public class HostUser {

    private String name;
    private String spotifyAccessToken;
    private String profilePicURL;
    private ArrayList<Playlist> playlistCollection;
    private Playlist activePlaylist;
    private String email;
    private String dbPassword;
    private String key;
    private String playlistToken;

    /**
     *
     * @param name Display name of current user, supplied by Spotify Database
     * @param spotifyAccessToken Token to allow user access to Spotify database, music library, and music playback
     * @param profilePicURL URL of user's Spotify profile picture, hosted by Spotify
     * @param email Email of user's Spotify profile
     * @param dbPassword User's password to authenticate with Firebase database
     */
    public HostUser(String name, String spotifyAccessToken, String profilePicURL, String email, String dbPassword)
    {
        this.name = name;
        this.spotifyAccessToken = spotifyAccessToken;
        this.profilePicURL = profilePicURL;
        this.playlistCollection = new ArrayList<>();
        this.email = email;
        this.dbPassword = dbPassword;
        this.playlistToken = email.split("@")[0];
    }

    /**
     *
     * @return Token to allow user access to Spotify database, music library, and music playback
     */
    public String getSpotifyAccessToken() {
        return spotifyAccessToken;
    }

    /**
     *
     * @return URL of user's Spotify profile picture, hosted by Spotify
     */
    public String getProfilePicURL() {
        return profilePicURL;
    }

    /**
     *
     * @return Display name of current user, supplied by Spotify Database
     */
    public String getUserName() {
        return name;
    }

    /**
     *
     * @param newActivePlaylist The playlist currently hosted by the user
     */
    public void setActivePlaylist(Playlist newActivePlaylist) {
        this.activePlaylist = newActivePlaylist;
        playlistCollection.add(newActivePlaylist);
    }


    /**
     *
     * @return Email of user's Spotify profile
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return User's password to authenticate with Firebase database
     */
    public String getDbPassword() {
        return dbPassword;
    }


    /**
     *
     * @param dbPassword User's password to authenticate with Firebase database
     */
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    /**
     *
     * @return The playlist currently hosted by the user
     */
    public Playlist getActivePlaylist() {
        return activePlaylist;
    }

    /**
     *
     * @return The key of the user object in the database
     */
    public String getKey() {
        return key;
    }

    /**
     *
     * @return The key of the user object in the database
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     *
     * @return Token to allow user access to Spotify database, music library, and music playback
     */
    public String getPlaylistToken() {
        return playlistToken;
    }

    /**
     *
     * @param playlistToken Token to allow user access to Spotify database, music library, and music playback
     */
    public void setPlaylistToken(String playlistToken) {
        this.playlistToken = playlistToken;
    }
}
