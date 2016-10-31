package com.inrotation.andrew.inrotation.Model;

import java.util.ArrayList;

/**
 * Created by andrewcofano on 10/30/16.
 */

public class PlaylistCollection {


    private String collectionName;
    private int playlistCount;
    private ArrayList<Playlist> playlists;

    public PlaylistCollection(String collectionName) {
        this.collectionName = collectionName;
    }
}
