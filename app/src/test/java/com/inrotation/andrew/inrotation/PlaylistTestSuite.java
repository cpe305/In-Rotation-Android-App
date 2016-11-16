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

        Playlist newPlaylist = new Playlist("Best Playlist", new HostUser("Andrew", "12345", "url/spotifyPic"), 3, 50000);


        assertEquals(newPlaylist.playlistName, "Best Playlist");
        assertEquals(newPlaylist.owner.getUserName(), "Andrew");
        assertEquals(newPlaylist.songCount, 3);
        assertEquals(newPlaylist.playlistDuration, 50000);
    }
}
