package com.inrotation.andrew.inrotation.data_access;

import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;


/**
 * Represents the Spotify Authenticator to use during the login process
 * Created by Andrew Cofano on 11/17/16.
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
        return builder.build();
    }




}
