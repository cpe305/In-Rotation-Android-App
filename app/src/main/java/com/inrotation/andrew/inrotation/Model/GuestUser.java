package com.inrotation.andrew.inrotation.model;

/**
 * Created by Andrew on 10/30/16.
 */

public class GuestUser extends User {

    private Playlist attendedPlaylist;

    public GuestUser(String name) {
        this.name = name;
    }
}
