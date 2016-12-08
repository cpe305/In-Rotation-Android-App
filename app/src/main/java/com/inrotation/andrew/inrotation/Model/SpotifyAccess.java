package com.inrotation.andrew.inrotation.model;

import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the singleton instance of the main access to the Spotify assets.
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

    /**
     *
     * @return The singleton instance of the main access to the Spotify assets
     */
    public static synchronized SpotifyAccess getInstance() {
        if (playerInstance == null) {
            playerInstance = new SpotifyAccess();
        }
        return playerInstance;
    }

    /**
     *
     * @param spoitfyPlayerInstance The Spotify Android SDK Music Player object to play tracks from Spotify Library
     */
    public void setSpotifyPlayer(Player spoitfyPlayerInstance) {
        spotifyPlayer = spoitfyPlayerInstance;
    }

    /**
     * @return The Spotify clientID of the current In Rotation Applcation instance
     */
    public String getClientId() {
        return clientId;
    }

    /**
     *
     * @param clientId The Spotify clientID of the current In Rotation Application instance
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     *
     * @return The accessToken that allows a user to access the Spotify Music Library
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param accessToken The accessToken that allows a user to access the Spotify Music Library
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     *
     * @return The Spotify Android SDK Music Player object to play tracks from Spotify Library
     */
    public Player getSpotifyPlayer() {
        return spotifyPlayer;
    }

    /**
     *
     * @return The current application user
     */
    public HostUser getSpotifyUser() {
        return spotifyUser;
    }

    /**
     *
     * @param spotifyUser The current application user
     */
    public void setSpotifyUser(HostUser spotifyUser) {
        this.spotifyUser = spotifyUser;
    }

    /**
     *
     * @return The current song playing in the Spotify SDK Music Player
     */
    public Song getCurrentSong() {
        return currentSong;
    }

    /**
     *
     * @return The current song to play in the Spotify SDK Music Player
     */
    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    /**
     *
     * @return The list of the playlist songs currently hosted in a playlist
     */
    public List<Song> getSongList() {
        return songList;
    }

    /**
     *
     * @param songList The list of the playlist songs currently hosted in a playlist
     */
    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }

    /**
     *
     * @return The current playlist that is being hosted by the user
     */
    public String getCurrentPlaylist() {
        return currentPlaylist;
    }

    /**
     *
     * @param currentPlaylist The current playlist that is to be hosted by the user
     */
    public void setCurrentPlaylist(String currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    /**
     *
     * @return The index of the current song in the currently hosted playlist
     */
    public int getCurrentSongIndex() {
        return currentSongIndex;
    }

    /**
     *
     * @param currentSongIndex The index of the current song in the currently hosted playlist
     */
    public void setCurrentSongIndex(int currentSongIndex) {
        this.currentSongIndex = currentSongIndex;
    }
}
