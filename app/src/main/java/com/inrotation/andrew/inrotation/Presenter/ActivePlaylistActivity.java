package com.inrotation.andrew.inrotation.Presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.inrotation.andrew.inrotation.Model.Song;
import com.inrotation.andrew.inrotation.Model.SpotifyAccess;
import com.inrotation.andrew.inrotation.R;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.ArrayList;

public class ActivePlaylistActivity extends AppCompatActivity {

    protected Song firstSong;
    protected ArrayList<Song> songList;
    protected String playlistName;
    protected ListView mListView;
    protected Player mPlayer;

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

        SearchListAdapter adapter = new SearchListAdapter(this, songList);
        Log.d("SongMatches", songList.toString());
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateSongListView();
        SpotifyAccess spotifyAccessInstance = SpotifyAccess.getInstance();
        mPlayer = spotifyAccessInstance.getSpotifyPlayer();
        mPlayer.playUri(null, firstSong.songURI, 0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
