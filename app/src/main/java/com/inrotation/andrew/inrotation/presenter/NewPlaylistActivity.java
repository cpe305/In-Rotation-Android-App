package com.inrotation.andrew.inrotation.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inrotation.andrew.inrotation.model.Song;
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

    private static final String SPOTIFY_SEARCH_URL_STANDARD = "https://api.spotify.com/v1/search?q=";
    private ListView mListView;

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
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextName.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
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
        mListView = (ListView) findViewById(R.id.songSearchListView);

        String[] searchWords = processSearchInput(searchInput);
        final ArrayList<Song> songMatches = obtainSongMatches(searchWords);
        SearchListAdapter adapter = new SearchListAdapter(this, songMatches);
        Log.d("SongMatches", songMatches.toString());
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Song selectedSong = songMatches.get(position);
                startActivePlaylist(selectedSong);
            }
        });
    }

    protected String[] processSearchInput(String searchInput) {
        String[] searchWords = searchInput.split(" ");

        Log.d("processSearchInput", searchInput);
        return searchWords;

    }


    protected void startActivePlaylist(Song selectedSong) {
        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("firstSong", selectedSong);
        b.putString("playlistName", getSupportActionBar().getTitle().toString());
        i.putExtras(b);
        i.setClass(this, ActivePlaylistActivity.class);
        startActivity(i);


        //Sending first song to new active playlist
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
                            throw new RuntimeException("context");
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
            e.printStackTrace();
            throw new RuntimeException("context");
        }

    }


    protected ArrayList<String> createAlbumCoverList(JSONArray albumCovers) {
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

    protected String createQuerySearchURL(String[] queryWords) {
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


    protected void ExpectPlaylistConfigInput() {
        final EditText editTextName = (EditText) findViewById(R.id.editPlaylistName);
        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setPlaylistName(editTextName.getText().toString());
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextName.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
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
