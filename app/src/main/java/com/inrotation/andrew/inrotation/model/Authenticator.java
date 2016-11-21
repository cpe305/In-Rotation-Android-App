package com.inrotation.andrew.inrotation.model;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.inrotation.andrew.inrotation.presenter.HomeScreenActivity;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

/**
 * Created by Andrew on 11/17/16.
 */

public class Authenticator {

    private static final String CLIENT_ID = "11d0740d0e1442a59b1265510a5ba218";
    private static final String REDIRECT_URI = "in-rotation://callback";
    public static final int REQUEST_CODE = 1337;


    public Authenticator() {

    }


    public AuthenticationRequest openSpotifyAuthenticateRequest() {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "user-read-birthdate", "user-read-email", "streaming"});
        AuthenticationRequest request = builder.build();

        return request;
    }




}
