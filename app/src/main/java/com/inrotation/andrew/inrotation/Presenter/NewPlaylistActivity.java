package com.inrotation.andrew.inrotation.Presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inrotation.andrew.inrotation.Model.Song;
import com.inrotation.andrew.inrotation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Andrew on 10/18/16.
 */

public class NewPlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_playlist);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("New Playlist");
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        ExpectPlaylistConfigInput();
        ExpectFirstSongPick();

    }

    protected void ExpectFirstSongPick() {
        final EditText editTextName = (EditText) findViewById(R.id.FirstSongNameSearch);
        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presentSongMatches(editTextName.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });
        Button doneButton = (Button) findViewById(R.id.PlaylistNameDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setPlaylistName(editTextName.getText().toString());
            }
        });
    }

    protected void presentSongMatches(String searchInput) {

        String[] searchWords = processSearchInput(searchInput);
        ArrayList<Song> songMatches = obtainSongMatches(searchWords);

    }

    protected String[] processSearchInput(String searchInput) {
        String[] searchWords = searchInput.split(" ");

        Log.d("processSearchInput", searchInput);
        return searchWords;

    }

    protected ArrayList<Song> obtainSongMatches(String[] queryWords) {

        RequestQueue queue = Volley.newRequestQueue(this);

        final ArrayList<Song> returnArray = new ArrayList<>();
        String url = createQuerySearchURL(queryWords);
       // String url = "https://api.spotify.com/v1/search?q=blessings+big+sean&type=track&limit=3";

        // Request a string response from the provided URL.

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

                        } catch (JSONException e) {
                            e.printStackTrace();
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


    protected Song createSongOf(JSONObject jsonTrack) {
        Song newSong = null;

        try {
            String songName = jsonTrack.getString("name");
            JSONArray artistList = jsonTrack.getJSONArray("artists");
            String artist = artistList.getJSONObject(0).getString("name");
            String album = jsonTrack.getJSONObject("album").getString("name");
            JSONArray albumCoverList = jsonTrack.getJSONArray("images");
            ArrayList<String> albumCovers = createAlbumCoverList(albumCoverList);
            int duration =  jsonTrack.getInt("duration_ms");
            String songURI = jsonTrack.getString("uri");
            boolean explicit = jsonTrack.getBoolean("explicit");

            newSong = new Song(songName, artist, album, duration, albumCovers, songURI, explicit);
            return newSong;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return newSong;

    }


    protected ArrayList<String> createAlbumCoverList(JSONArray albumCovers) {
        ArrayList<String> albumCoverURLs = new ArrayList<>();

        for (int i = 0; i < albumCovers.length(); i++) {
            try {
                albumCoverURLs.add(albumCovers.getJSONObject(i).getString("url"));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return albumCoverURLs;
    }

    protected String createQuerySearchURL(String[] queryWords) {
        int index;
        String trackSearchURL = "https://api.spotify.com/v1/search?q=";


        index = 0;
        while (index < queryWords.length) {
            if (index != (queryWords.length - 1)) {
                trackSearchURL += (queryWords[index] + "+");
            }
            else {
                trackSearchURL += (queryWords[index]);
            }
            index++;
        }
        trackSearchURL += "&type=track&limit=3";

        return trackSearchURL;
    }


    protected void ExpectPlaylistConfigInput() {
        final EditText editTextName = (EditText) findViewById(R.id.editPlaylistName);
        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setPlaylistName(editTextName.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });
        Button doneButton = (Button) findViewById(R.id.PlaylistNameDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Done buton", "clicked");
                setPlaylistName(editTextName.getText().toString());
            }
        });


    }


    protected void setPlaylistName(String newName) {
        if (!newName.isEmpty()) {
            getSupportActionBar().setTitle(newName);
        }

    }



}
