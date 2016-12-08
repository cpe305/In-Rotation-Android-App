package com.inrotation.andrew.inrotation.model;


import java.util.ArrayList;

/**
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


    public String getSpotifyAccessToken() {
        return spotifyAccessToken;
    }


    public String getProfilePicURL() {
        return profilePicURL;
    }


    public String getUserName() {
        return name;
    }


    public void setActivePlaylist(Playlist newActivePlaylist) {
        this.activePlaylist = newActivePlaylist;
        playlistCollection.add(newActivePlaylist);
    }


    public String getEmail() {
        return email;
    }


    public String getDbPassword() {
        return dbPassword;
    }


    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }


    public Playlist getActivePlaylist() {
        return activePlaylist;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPlaylistToken() {
        return playlistToken;
    }

    public void setPlaylistToken(String playlistToken) {
        this.playlistToken = playlistToken;
    }
}