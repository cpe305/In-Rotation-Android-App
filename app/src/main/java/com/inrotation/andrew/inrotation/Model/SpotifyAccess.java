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
    private HostUser spotifyUser;
    private Song currentSong;


    private SpotifyAccess() {
        this.accessToken = "";
        this.clientId = "";
        this.currentSong = null;
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

    public HostUser getSpotifyUser() {
        return spotifyUser;
    }

    public void setSpotifyUser(HostUser spotifyUser) {
        this.spotifyUser = spotifyUser;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }
}
