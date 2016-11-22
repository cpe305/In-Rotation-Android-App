package com.inrotation.andrew.inrotation.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inrotation.andrew.inrotation.model.Playlist;
import com.inrotation.andrew.inrotation.model.SearchLibrary;
import com.inrotation.andrew.inrotation.model.Song;
import com.inrotation.andrew.inrotation.model.SpotifyAccess;
import com.inrotation.andrew.inrotation.R;
import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;

public class ActivePlaylistActivity extends AppCompatActivity {

    protected Song firstSong;
    protected ArrayList<Song> songList;
    protected String playlistName;
    protected ListView mListView;
    protected Player mPlayer;
    protected SearchListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        songList = new ArrayList<>();
        Bundle b = this.getIntent().getExtras();

        if (b != null) {
            firstSong = (Song) b.get("firstSong");
            songList.add(firstSong);
            playlistName = b.getString("playlistName");
        }



        setContentView(R.layout.activity_active_playlist);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle(playlistName);

        SpotifyAccess spotifyAccessInstance = SpotifyAccess.getInstance();
        mPlayer = spotifyAccessInstance.getSpotifyPlayer();
        mPlayer.playUri(null, firstSong.songURI, 0, 0);
    }

    protected void populateSongListView() {
        mListView = (ListView) findViewById(R.id.songListView);

        adapter = new SearchListAdapter(this, songList);
        Log.d("SongMatches", songList.toString());
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateSongListView();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/playlists/0001/currentSong/next");
// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Song post = dataSnapshot.getValue(Song.class);
                songList.add(post);
                adapter.setmDataSource(songList);
                mListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        SpotifyAccess spotifyAccessInstance = SpotifyAccess.getInstance();
        mPlayer = spotifyAccessInstance.getSpotifyPlayer();
        if (!mPlayer.getPlaybackState().isPlaying) {
            mPlayer.playUri(null, firstSong.songURI, 0, 0);
        }

    }


}
