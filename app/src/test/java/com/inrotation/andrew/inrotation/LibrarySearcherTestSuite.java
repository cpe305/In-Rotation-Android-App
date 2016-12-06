package com.inrotation.andrew.inrotation;

import com.inrotation.andrew.inrotation.model.Playlist;
import com.inrotation.andrew.inrotation.model.Song;
import com.inrotation.andrew.inrotation.presenter.LibrarySearcher;
import com.inrotation.andrew.inrotation.presenter.NewPlaylistActivity;

import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by andrewcofano on 12/5/16.
 */

public class LibrarySearcherTestSuite {


    @Test
    public void testCreateQuerySearch() throws Exception {

        String[] queryWords = {"blessings", "Chance", "Coloring"};
        String correctString = "https://api.spotify.com/v1/search?q=blessings+Chance+Coloring";

        String testResult = LibrarySearcher.createQuerySearchURL(queryWords);
        assertEquals(testResult, correctString);

    }



}
