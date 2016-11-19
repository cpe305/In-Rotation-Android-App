package com.inrotation.andrew.inrotation.model;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by andrewcofano on 11/18/16.
 */

public class SearchLibrary {

    private static final String SPOTIFY_SEARCH_URL_STANDARD = "https://api.spotify.com/v1/search?q=";


    public static ArrayList<Song> obtainSongMatches(String[] queryWords, Context context) {

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(context);

        final ArrayList<Song> returnArray = new ArrayList<>();
        String url = createQuerySearchURL(queryWords);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());

                        try {
                            JSONObject allTracksObject = response.getJSONObject("tracks");
                            JSONArray trackElements = allTracksObject.getJSONArray("items");
                            for (int i = 0; i < trackElements.length(); i++) {
                                JSONObject singleTrack = trackElements.getJSONObject(i);
                                Song trackObject = createSongOf(singleTrack);
                                returnArray.add(trackObject);

                            }
                        } catch (MyJSONException e) {
                            Log.d("Error", e.toString());
                        } catch (JSONException e) {
                            Log.d("Error", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //mSongNameView.setText("That didn't work!" + error.toString());
                Log.d("Volley", error.toString());
            }
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new ArrayMap<>();
                params.put("Accept", "application/json");
                //..add other headers
                return params;
            }
        };

        queue.add(objectRequest);

        return returnArray;
    }

    protected static String createQuerySearchURL(String[] queryWords) {
        int index;
        StringBuilder trackSearchURL = new StringBuilder();
        trackSearchURL.append(SPOTIFY_SEARCH_URL_STANDARD);

        index = 0;
        while (index < queryWords.length) {
            if (index != (queryWords.length - 1)) {
                trackSearchURL.append(queryWords[index]);
                trackSearchURL.append("+");
            }
            else {
                trackSearchURL.append(queryWords[index]);
            }
            index++;
        }
        trackSearchURL.append("&type=track&limit=3");

        return trackSearchURL.toString();
    }

    protected static Song createSongOf(JSONObject jsonTrack) throws MyJSONException {
        Song newSong = null;

        try {
            String songName = jsonTrack.getString("name");
            JSONArray artistList = jsonTrack.getJSONArray("artists");
            String artist = artistList.getJSONObject(0).getString("name");
            JSONObject album = jsonTrack.getJSONObject("album");
            String albumName = album.getString("name");
            JSONArray albumCoverList = album.getJSONArray("images");
            ArrayList<String> albumCovers = createAlbumCoverList(albumCoverList);
            int duration =  jsonTrack.getInt("duration_ms");
            String songURI = jsonTrack.getString("uri");
            boolean explicit = jsonTrack.getBoolean("explicit");

            newSong = new Song(songName, artist, albumName, duration, albumCovers, songURI, explicit);
            return newSong;
        }
        catch (JSONException e) {
            Log.d("Error", e.toString());
            throw new MyJSONException("Something went wrong");
        }

    }


    protected static ArrayList<String> createAlbumCoverList(JSONArray albumCovers) {
        ArrayList<String> albumCoverURLs = new ArrayList<>();

        for (int i = 0; i < albumCovers.length(); i++) {
            try {
                albumCoverURLs.add(albumCovers.getJSONObject(i).getString("url"));
            }
            catch (JSONException e) {
                e.printStackTrace();
                throw new RuntimeException("context");
            }
        }

        return albumCoverURLs;
    }

}
