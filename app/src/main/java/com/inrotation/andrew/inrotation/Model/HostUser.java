package com.inrotation.andrew.inrotation.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

/**
 * Created by Andrew on 10/30/16.
 */

public class HostUser extends User {

    private String name;
    private String spotifyAccessToken;
    private String profilePicURL;
    private ArrayList<Playlist> playlistCollection;
    private Playlist activePlaylist;
    private String email;
    private String dbPassword;

    public HostUser(String name, String spotifyAccessToken, String profilePicURL, String email, String dbPassword)
    {
        this.name = name;
        this.spotifyAccessToken = spotifyAccessToken;
        this.profilePicURL = profilePicURL;
        this.playlistCollection = new ArrayList<>();
        this.email = email;
        this.dbPassword = dbPassword;
    }

    @Exclude
    public String getSpotifyAccessToken() {
        return spotifyAccessToken;
    }

    @Exclude
    public String getProfilePicURL() {
        return profilePicURL;
    }

    @Exclude
    public String getUserName() {
        return name;
    }

    @Exclude
    public void setActivePlaylist(Playlist newActivePlaylist) {
        this.activePlaylist = newActivePlaylist;
        playlistCollection.add(newActivePlaylist);
    }

    @Exclude
    public String getEmail() {
        return email;
    }

    @Exclude
    public String getDbPassword() {
        return dbPassword;
    }

    @Exclude
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    @Exclude
    public Playlist getActivePlaylist() {
        return activePlaylist;
    }
}
