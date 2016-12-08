package com.inrotation.andrew.inrotation.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import java.util.Map;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.inrotation.andrew.inrotation.data_access.Authenticator;
import com.inrotation.andrew.inrotation.data_access.DatabaseModifier;
import com.inrotation.andrew.inrotation.model.HostUser;
import com.inrotation.andrew.inrotation.data_access.RequestQueue;
import com.inrotation.andrew.inrotation.model.SpotifyAccess;
import com.inrotation.andrew.inrotation.R;
import com.inrotation.andrew.inrotation.model.SpotifyProfileBuilder;
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
 * Represents the activity that takes care of the HostUser's information and application homescreen.
 * Created by Andrew Cofano
 */
public class HomeScreenActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    private static final String CLIENT_ID = "11d0740d0e1442a59b1265510a5ba218";

    private Player mPlayer;
    private String resAccessToken;
    private Authenticator userAuthenticator;
    private PlaylistManager playlistManager;
    private ImageButton playButton, rewindButton, fastForwardButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.my_home_title);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // HostUser is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // HostUser is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        onAuthenticateClick();

        playlistManager = new PlaylistManager();

    }

    private void setPlayerControls() {
        playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playlistManager.onPlayButtonClicked(playButton);
            }
        });

        rewindButton = (ImageButton) findViewById(R.id.rewindButton);
        rewindButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playlistManager.playPreviousSong();
            }
        });

        fastForwardButton = (ImageButton) findViewById(R.id.fastforwardButton);
        fastForwardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playlistManager.playNextSong();
            }
        });
    }


    protected void onAuthenticateClick() {
        userAuthenticator = new Authenticator();
        AuthenticationRequest request = userAuthenticator.openSpotifyAuthenticateRequest();

        AuthenticationClient.openLoginActivity(this, Authenticator.REQUEST_CODE, request);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == Authenticator.REQUEST_CODE) {
            AuthenticationResponse authenRes = AuthenticationClient.getResponse(resultCode, intent);
            if (authenRes.getType() == AuthenticationResponse.Type.TOKEN) {
                resAccessToken = authenRes.getAccessToken();
                SpotifyAccess spotifyAccessInstance = SpotifyAccess.getInstance();
                spotifyAccessInstance.setAccessToken(resAccessToken);
                spotifyAccessInstance.setClientId(CLIENT_ID);
                Config playerConfig = new Config(this, authenRes.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(HomeScreenActivity.this);
                        mPlayer.addNotificationCallback(HomeScreenActivity.this);
                        SpotifyAccess spotifyAccessInstance = SpotifyAccess.getInstance();
                        spotifyAccessInstance.setSpotifyPlayer(mPlayer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("HomeScreenActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onResume() {
        super.onStart();
        ViewRefresher.refreshPlayerBar(this, this);

    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();

    }


    /**
     *
     * @param playerEvent represents the state that the Spotify Player has changed to
     */
    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("HomeScreenActivity", "Playback event received: " + playerEvent.name());
        if (playerEvent == PlayerEvent.kSpPlaybackNotifyAudioDeliveryDone) {
            PlaylistManager manager = new PlaylistManager();
            manager.playNextSong();
        }

    }

    /**
     *
     * @param error represents the error the Spotify Player has delivered
     */
    @Override
    public void onPlaybackError(Error error) {
        Log.d("HomeScreenActivity:", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    /**
     * This method runs when the Spotify authentication has finished and creates the current HostUser object
     */
    @Override
    public void onLoggedIn() {

        Log.d("HomeScreenActivity", "HostUser logged in");

        // Instantiate the RequestQueue.
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.spotify.com/v1/me";

        // Request a string response from the provided URL.

        JsonObjectRequest arrayRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());

                        try {


                            SpotifyProfileBuilder profileBuilder = new SpotifyProfileBuilder();

                            HostUser createdUser = profileBuilder.buildSpotifyProfile(response);
                            if (createdUser != null) {
                                loadUserNameView(createdUser.getUserName());
                                loadProfilePic(createdUser.getProfilePicURL());
                                processFirebaseLogin(createdUser);
                                SpotifyAccess access = SpotifyAccess.getInstance();
                                if (access.getCurrentPlaylist().equals(access.getSpotifyUser().getPlaylistToken())) {
                                    setPlayerControls();
                                }

                            }
                            else {
                                new AlertDialog.Builder(HomeScreenActivity.this)
                                        .setTitle("Could not load Spotify profile")
                                        .setMessage("Try logging back in again")
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }


                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Volley", error.toString());
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new ArrayMap<>();
                params.put("Authorization", "Bearer " + resAccessToken);
                //..add other headers
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(arrayRequest);

    }

    private void processFirebaseLogin(final HostUser createdUser) {

        SpotifyAccess accessInstance = SpotifyAccess.getInstance();
        String email = accessInstance.getSpotifyUser().getEmail();
        String password = accessInstance.getSpotifyUser().getDbPassword();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            DatabaseModifier dbModifier = new DatabaseModifier();
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.

                            dbModifier.addUserToDatabase(createdUser);
                        }
                    }
                });


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "signInWithEmail:failed", task.getException());
                            Toast.makeText(HomeScreenActivity.this, "Failed firebase authentication",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


        final FloatingActionButton newPlaylistButton = (FloatingActionButton) findViewById(R.id.AddSongActionButton);
        newPlaylistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newPlaylistIntent = new Intent(v.getContext(), NewPlaylistActivity.class);
                startActivity(newPlaylistIntent);
            }
        });

        final FloatingActionButton joinPlaylistButton = (FloatingActionButton) findViewById(R.id.JoinPlaylistActionButton);
        joinPlaylistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newPlaylistIntent = new Intent(v.getContext(), JoinPlaylistActivity.class);
                startActivity(newPlaylistIntent);
            }
        });

    }


    private void loadProfilePic(String imageURL) {
        //final ImageView userProfileView = (ImageView) findViewById(R.id.userProfilePicView);
        NetworkImageView mNetworkImageView;
        ImageLoader mImageLoader;

        // Get the NetworkImageView that will display the image.
        mNetworkImageView =(NetworkImageView) findViewById(R.id.userProfilePicView);
        // Get the ImageLoader through your singleton class.
        mImageLoader = RequestQueue.getInstance(this).getImageLoader();
        // Set the URL of the image that should be loaded into this view, and
        // specify the ImageLoader that will be used to make the request.
        mNetworkImageView.setImageUrl(imageURL, mImageLoader);

    }

    private void loadUserNameView(String userName) {
        final TextView userNameView = (TextView) findViewById(R.id.userProfileNameView);
        userNameView.setText(userName);

    }

    /**
     * Method to run when the Spotify account logs out of the In Rotation Application
     */
    @Override
    public void onLoggedOut() {
        Log.d("HomeScreenActivity", "HostUser logged out");
    }

    /**
     * Method to run when something went wrong during the Spotify account login process
     */
    @Override
    public void onLoginFailed(int i) {
        Log.d("HomeScreenActivity", "Login failed");
    }

    /**
     * Method to run when there is a temporary error during the Spotify account login process
     */
    @Override
    public void onTemporaryError() {
        Log.d("HomeScreenActivity", "Temporary error occurred");
    }

    /**
     * Method to run when the Spotify account login process finds a connection with Spotify database
     */
    @Override
    public void onConnectionMessage(String message) {
        Log.d("HomeScreenActivity", "Received connection message: " + message);
    }
}
