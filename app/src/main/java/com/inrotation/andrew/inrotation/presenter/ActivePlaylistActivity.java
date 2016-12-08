package com.inrotation.andrew.inrotation.presenter;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.inrotation.andrew.inrotation.model.Song;
import com.inrotation.andrew.inrotation.model.SpotifyAccess;
import com.inrotation.andrew.inrotation.R;
import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;
import java.util.Map;

/**
 * Represents the activity that handles the currently hosted playlist.
 * Created by Andrew Cofano
 */
public class ActivePlaylistActivity extends AppCompatActivity {

    protected ArrayList<Song> songList;
    protected ListView mListView;
    protected Player mPlayer;
    protected SearchListAdapter adapter;
    protected PlaylistManager playlistManager;
    protected ImageButton playButton, rewindButton, fastForwardButton;
    protected FloatingActionButton addSongButton;
    protected String playlistKey = "";
    private boolean isHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        songList = new ArrayList<>();
        Bundle b = this.getIntent().getExtras();

        if (b != null) {
            playlistKey = b.getString("playlistCode");
        }



        setContentView(R.layout.activity_active_playlist);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SpotifyAccess spotifyAccess = SpotifyAccess.getInstance();
        setPlaylistName(playlistKey, myToolbar);

        if (playlistKey.equals(spotifyAccess.getSpotifyUser().getPlaylistToken())) {
            isHost = true;
            setListViewOberserver();
            createButtonListeners();
            getCurrentSong(playButton);
        }
        else {
            setListViewOberserver();
            isHost = false;
        }
        ViewRefresher.refreshPlayerBar(this,this);
        addSongButton = (FloatingActionButton) findViewById(R.id.AddSongActionButton);
        addSongButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addSongIntent = new Intent(v.getContext(), AddSongActivity.class);
                addSongIntent.putExtra("playlistCode", playlistKey);
                startActivity(addSongIntent);
            }
        });

    }


    private void setListViewOberserver() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/playlists/"+playlistKey+"/songArrayList");
        // Attach a listener to read the data at our posts reference
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Song post = dataSnapshot.getValue(Song.class);
                songList.add(post);
                populateSongListView();
                adapter.setmDataSource(songList);
                mListView.setAdapter(adapter);

                SpotifyAccess access = SpotifyAccess.getInstance();

                if (isHost) {
                    access.getSongList().add(post);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

        });
    }

    private void createButtonListeners() {
        playlistManager = new PlaylistManager();
        playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playlistManager.onPlayButtonClicked(playButton);
            }
        });

        rewindButton = (ImageButton) findViewById(R.id.rewindButton);
        rewindButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playlistManager.playPreviousSong();
            }
        });

        fastForwardButton = (ImageButton) findViewById(R.id.fastforwardButton);
        fastForwardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playlistManager.playNextSong();
            }
        });
        ViewRefresher.refreshPlayerBar(this,this);

    }

    private void getCurrentSong(final ImageButton playButton) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final StringBuilder currentSongId = new StringBuilder();
        DatabaseReference ref = database.getReference("server/saving-data/playlists/"+playlistKey+"/");
        ref.child("currentSongIndex").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot snapshot) {
                currentSongId.append(snapshot.getValue(String.class));
                playCurrentSong(currentSongId.toString());

            }
            @Override
            public void onCancelled(DatabaseError arg0) {
                System.out.println("The read failed: " + arg0.getCode());
            }
        });
        ViewRefresher.refreshPlayerBar(this, this);


    }

    private void playCurrentSong(final String currentSongId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/playlists/"+playlistKey+"/");

        ref.child("songArrayList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                GenericTypeIndicator<Map<String, Song>> t = new GenericTypeIndicator<Map<String, Song>>() {};
                Map<String, Song> map = snapshot.getValue(t);

                // get the values from map.values();
                Song firstSong = map.get(currentSongId);
                SpotifyAccess spotifyAccessInstance = SpotifyAccess.getInstance();
                mPlayer = spotifyAccessInstance.getSpotifyPlayer();
                if (!mPlayer.getPlaybackState().isPlaying) {
                    spotifyAccessInstance.setCurrentSong(firstSong);



                    mPlayer.playUri(null, firstSong.songURI, 0, 0);
                    playlistManager.onPlayButtonClicked(playButton);
                }


            }
            @Override
            public void onCancelled(DatabaseError arg0) {
                System.out.println("The read failed: " + arg0.getCode());
            }
        });
    }

    protected void populateSongListView() {

        mListView = (ListView) findViewById(R.id.songListView);

        adapter = new SearchListAdapter(this, songList);
        mListView.setAdapter(adapter);
    }

    public void setPlaylistName(String code, final Toolbar mytoolbar) {
        //final StringBuilder playlistName = new StringBuilder();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/playlists/" + code+"/playlistName");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                getSupportActionBar().setTitle(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewRefresher.refreshPlayerBar(this, this);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewRefresher.refreshPlayerBar(this, this);


    }


}
