package com.inrotation.andrew.inrotation;

import com.inrotation.andrew.inrotation.model.Song;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class SongTestSuite {
    @Test
    public void testSong() throws Exception {
        ArrayList<String> albumcovers = new ArrayList<>();
        albumcovers.add("url1");
        albumcovers.add("url2");
        albumcovers.add("url3");
        Song newSong = new Song("Blessings", "Chance The Rapper", "Coloring Book", 3000, albumcovers, "songURI", true);


        assertEquals(newSong.songName, "Blessings");
        assertEquals(newSong.artists, "Chance The Rapper");
        assertEquals(newSong.album, "Coloring Book");
        assertEquals(newSong.duration, 3000);
    }

}