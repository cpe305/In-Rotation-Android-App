package com.inrotation.andrew.inrotation.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inrotation.andrew.inrotation.model.NewPlaylistCreator;
import com.inrotation.andrew.inrotation.model.Playlist;
import com.inrotation.andrew.inrotation.model.SearchLibrary;
import com.inrotation.andrew.inrotation.model.Song;
import com.inrotation.andrew.inrotation.R;
import com.inrotation.andrew.inrotation.model.SpotifyAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Andrew on 10/18/16.
 */

public class NewPlaylistActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String SPOTIFY_SEARCH_URL_STANDARD = "https://api.spotify.com/v1/search?q=";
    private ListView mListView;
    private Playlist createdPlaylist;
    private NewPlaylistCreator playlistCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_playlist);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("New Playlist");

        playlistCreator = new NewPlaylistCreator();
        playlistCreator.setPlaylistName(getSupportActionBar().getTitle().toString());

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInAnonymously:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "signInAnonymously", task.getException());
                            Toast.makeText(NewPlaylistActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ExpectPlaylistConfigInput();
        ExpectFirstSongPick();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
    }

    protected void presentSongMatches(String searchInput) {
        mListView = (ListView) findViewById(R.id.songSearchListView);

        final ArrayList<Song> songMatches = SearchLibrary.obtainSongMatches(searchInput.split(" "), this);
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


    protected void startActivePlaylist(Song selectedSong) {

        playlistCreator.setFirstPlaylistSong(selectedSong);
        createdPlaylist = playlistCreator.createNewPlaylist();
        SpotifyAccess accessInstance = SpotifyAccess.getInstance();
        accessInstance.getSpotifyUser().setActivePlaylist(createdPlaylist);

        Intent i = new Intent();
        Bundle b = new Bundle();

        b.putSerializable("firstSong", selectedSong);
        b.putString("playlistName", getSupportActionBar().getTitle().toString());
        i.putExtras(b);
        i.setClass(this, ActivePlaylistActivity.class);
        startActivity(i);


        //Sending first song to new active playlist
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
            playlistCreator.setPlaylistName(newName);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("message");
            mDatabase = FirebaseDatabase.getInstance().getReference();

            myRef.setValue("Hello, World!");
        }


    }



}
