package com.inrotation.andrew.inrotation.model;

import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.inrotation.andrew.inrotation.R;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlaybackState;
import com.spotify.sdk.android.player.Player;

import static android.R.drawable.ic_media_pause;
import static android.R.drawable.ic_media_play;

/**
 * Created by Andrew on 11/22/16.
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

    public void onPlayButtonClicked(ImageButton button) {
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


    public void onPreviousButtonClicked(ImageButton button) {
        SpotifyAccess access = SpotifyAccess.getInstance();
        Player player = access.getSpotifyPlayer();
        PlaybackState playbackState = player.getPlaybackState();

        player.skipToPrevious(mOperationCallback);
    }

    public void onNextButtonClicked(ImageButton button) {
        SpotifyAccess access = SpotifyAccess.getInstance();
        Player player = access.getSpotifyPlayer();
        PlaybackState playbackState = player.getPlaybackState();
        player.skipToNext(mOperationCallback);
    }


}
