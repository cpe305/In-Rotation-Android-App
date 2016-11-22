package com.inrotation.andrew.inrotation;
import com.inrotation.andrew.inrotation.model.HostUser;
import com.inrotation.andrew.inrotation.model.Playlist;
import com.inrotation.andrew.inrotation.model.Song;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


/**
 * Created by andrewcofano on 11/16/16.
 */

public class PlaylistTestSuite {

    @Test
    public void testPlaylist() throws Exception {

        ArrayList<String> albumcovers = new ArrayList<>();
        albumcovers.add("url1");
        albumcovers.add("url2");
        albumcovers.add("url3");

        Playlist newPlaylist = new Playlist("Best Playlist", "Andrew",
                new Song("Blessings", "Chance The Rapper", "Coloring Book", 3000, albumcovers, "songURI", true));


        assertEquals(newPlaylist.playlistName, "Best Playlist");
        assertEquals(newPlaylist.owner, "Andrew");

    }
}
