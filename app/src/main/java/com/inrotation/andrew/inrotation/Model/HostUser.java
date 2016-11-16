package com.inrotation.andrew.inrotation.model;

import java.util.ArrayList;

/**
 * Created by Andrew on 10/30/16.
 */

public class HostUser extends User {


    private String spotifyAccessToken;
    private String profilePicURL;
    private ArrayList<Playlist> playlistCollection;

    public HostUser(String name, String spotifyAccessToken, String profilePicURL)
    {
        this.name = name;
        this.spotifyAccessToken = spotifyAccessToken;
        this.profilePicURL = profilePicURL;
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

}
