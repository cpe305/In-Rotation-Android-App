package com.inrotation.andrew.inrotation.presenter;

import android.util.Log;
import android.widget.ImageButton;

import com.inrotation.andrew.inrotation.model.Song;
import com.inrotation.andrew.inrotation.model.SpotifyAccess;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlaybackState;
import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;

import static android.R.drawable.ic_media_pause;
import static android.R.drawable.ic_media_play;

/**
 * Represents the manager of a playlist, handling song additions, changes, and music playback from the playlist
 * Created by Andrew Cofano on 11/22/16.
 */
public class PlaylistManager {

    private final Player.OperationCallback mOperationCallback = new Player.OperationCallback() {
        @Override
        public void onSuccess() {
            Log.d("PlaylistManager", "OK!");
        }

        @Override
        public void onError(Error error) {
            Log.e("PlaylistManager", "Error" + error);
        }

    };

    public PlaylistManager() {

    }

    protected void onPlayButtonClicked(ImageButton button) {
        SpotifyAccess access = SpotifyAccess.getInstance();
        Player player = access.getSpotifyPlayer();
        PlaybackState playbackState = player.getPlaybackState();

        if (playbackState != null && playbackState.isPlaying) {
            player.pause(mOperationCallback);
            button.setBackgroundResource(ic_media_play);
        }
        else {
            player.resume(mOperationCallback);
            button.setBackgroundResource(ic_media_pause);
        }


    }


    protected void playPreviousSong() {
        SpotifyAccess access = SpotifyAccess.getInstance();
        Player player = access.getSpotifyPlayer();
        ArrayList<Song> list = (ArrayList<Song>)(access.getSongList());
        int currentIndex = access.getCurrentSongIndex();
        if (currentIndex == 0) {
            player.playUri(null, list.get(currentIndex).songURI, 0, 0);
        }
        else {
            int newIndex = currentIndex - 1;
            Song prevSong = list.get(newIndex);
            access.setCurrentSongIndex(newIndex);
            player.playUri(null, prevSong.songURI, 0, 0);
            access.setCurrentSong(prevSong);
        }

    }

    protected void playNextSong() {
        SpotifyAccess access = SpotifyAccess.getInstance();
        Player player = access.getSpotifyPlayer();

        ArrayList<Song> list = (ArrayList<Song>)access.getSongList();
        int currentIndex = access.getCurrentSongIndex();
        if (currentIndex < list.size() - 1) {
            int newIndex = currentIndex + 1;
            Song nextSong = list.get(newIndex);
            access.setCurrentSongIndex(newIndex);
            player.playUri(null, nextSong.songURI, 0, 0);
            access.setCurrentSong(nextSong);
        }
    }

}
