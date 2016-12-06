package com.inrotation.andrew.inrotation.presenter;

import android.app.Activity;
import android.content.Context;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.inrotation.andrew.inrotation.R;
import com.inrotation.andrew.inrotation.data_access.RequestQueue;
import com.inrotation.andrew.inrotation.model.Song;
import com.inrotation.andrew.inrotation.model.SpotifyAccess;
import com.spotify.sdk.android.player.Player;

/**
 * Created by Andrew on 11/30/16.
 */

public class ViewRefresher {

    public static void refreshPlayerBar(Activity activity, Context context) {
        SpotifyAccess spotifyAccess = SpotifyAccess.getInstance();
        Player player = spotifyAccess.getSpotifyPlayer();

        Song currentSong = spotifyAccess.getCurrentSong();
        if (currentSong != null) {
            String albumURL = currentSong.albumCoverURLs.get(2);
            NetworkImageView currentSongCoverView = (NetworkImageView) activity.findViewById(R.id.playerBarCoverView);
            ImageLoader mImageLoader = RequestQueue.getInstance(context).getImageLoader();

            currentSongCoverView.setImageUrl(albumURL, mImageLoader);
        }
    }
}
