package com.inrotation.andrew.inrotation.model;

import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;
import java.util.List;

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
    private int currentSongIndex;
    private List<Song> songList;
    private String currentPlaylist;


    private SpotifyAccess() {
        this.accessToken = "";
        this.clientId = "";
        this.currentSong = null;
        this.currentPlaylist = "";
        this.currentSongIndex = 0;
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

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }

    public String getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(String currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    public int getCurrentSongIndex() {
        return currentSongIndex;
    }

    public void setCurrentSongIndex(int currentSongIndex) {
        this.currentSongIndex = currentSongIndex;
    }
}
