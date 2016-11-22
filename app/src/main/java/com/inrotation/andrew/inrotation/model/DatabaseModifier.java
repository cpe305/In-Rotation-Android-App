package com.inrotation.andrew.inrotation.model;

import com.google.android.gms.location.places.PlaceReport;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew on 11/20/16.
 */
public class DatabaseModifier {

    private FirebaseDatabase database;

    public DatabaseModifier() {
        this.database = FirebaseDatabase.getInstance();
    }


    public void addUserToDatabase(HostUser createdUser) {
        DatabaseReference ref = database.getReference("server/saving-data");
        DatabaseReference usersRef = ref.child("users");

        Map<String, HostUser> users = new HashMap<String, HostUser>();
        users.put(String.valueOf(createdUser.hashCode()), createdUser);
        usersRef.setValue(users);
    }

    public void addPlaylist(Playlist createdPlaylist) {
        DatabaseReference ref = database.getReference("server/saving-data");
        DatabaseReference playRef = ref.child("playlists");


        Map<String, Playlist> playlist = new HashMap<>();
        playlist.put("0001", createdPlaylist);

        //playlist.put(String.valueOf(createdPlaylist.hashCode()), createdPlaylist);
        playRef.setValue(playlist);
    }
}
