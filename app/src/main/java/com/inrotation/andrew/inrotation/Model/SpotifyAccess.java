package com.inrotation.andrew.inrotation.model;

import com.spotify.sdk.android.player.Player;

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
