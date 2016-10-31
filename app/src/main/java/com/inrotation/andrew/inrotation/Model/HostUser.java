package com.inrotation.andrew.inrotation.Model;

import java.util.ArrayList;

/**
 * Created by Andrew on 10/30/16.
 */

public class HostUser extends User {


    private String spotifyAccessToken;
    private String profilePicURL;
    private ArrayList<Playlist> playlistCollection;

    public HostUser(String name, String spotifyAccessToken, String profilePicURL) {
        this.name = name;
        this.spotifyAccessToken = spotifyAccessToken;
        this.profilePicURL = profilePicURL;
    }
}
