package com.inrotation.andrew.inrotation.Model;

import android.content.Context;
import android.util.Log;

import com.inrotation.andrew.inrotation.Presenter.HomeScreenActivity;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

/**
 * Created by andrewcofano on 11/3/16.
 */

public class SpotifyAccess {

    private static SpotifyAccess playerInstance = null;
    private String accessToken;
    private String clientId;
    private Player spotifyPlayer;


    private SpotifyAccess() {
        this.accessToken = "";
        this.clientId = "";
    }

    public static synchronized SpotifyAccess getInstance() {
        if (playerInstance == null) {
            playerInstance = new SpotifyAccess();
        }
        return playerInstance;
    }

    public void setSpotifyPlayer(Player spoitfyPlayerInstance) {
        spotifyPlayer = spoitfyPlayerInstance;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Player getSpotifyPlayer() {
        return spotifyPlayer;
    }
}
