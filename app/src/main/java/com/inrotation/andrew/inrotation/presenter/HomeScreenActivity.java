package com.inrotation.andrew.inrotation.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Map;

import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/*import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;*/
import com.inrotation.andrew.inrotation.model.Authenticator;
import com.inrotation.andrew.inrotation.model.RequestQueue;
import com.inrotation.andrew.inrotation.model.SpotifyAccess;
import com.inrotation.andrew.inrotation.R;
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

public class HomeScreenActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    private static final String CLIENT_ID = "11d0740d0e1442a59b1265510a5ba218";
    private static final String REDIRECT_URI = "in-rotation://callback";

    private Player mPlayer;
    private String resAccessToken;
    private AuthenticationResponse authenRes;
    private Authenticator spotifyAuthenticator;

    private static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("My Home");

        onAuthenticateClick();

        final FloatingActionButton newPlaylistButton = (FloatingActionButton) findViewById(R.id.CreatePlaylistActionButton);
        newPlaylistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newPlaylistIntent = new Intent(v.getContext(), NewPlaylistActivity.class);
                startActivity(newPlaylistIntent);
            }
        });
    }


    protected void onAuthenticateClick() {
        spotifyAuthenticator = new Authenticator();
        AuthenticationRequest request = spotifyAuthenticator.openAuthenticateRequest();

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
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();

    }


    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("HomeScreenActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("HomeScreenActivity:", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {

        Log.d("HomeScreenActivity", "User logged in");

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

                            JSONArray profilePic = response.getJSONArray("images");
                            String userName = response.getString("display_name");
                            JSONObject profilePicObject = profilePic.getJSONObject(0);
                            String profilePicURL = profilePicObject.getString("url");
                            Log.d("Profile Pic", profilePicURL);
                            loadUserNameView(userName);
                            loadProfilePic(profilePicURL);


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                           // throw new JSONObjectException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //mSongNameView.setText("That didn't work!" + error.toString());
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


    public void loadProfilePic(String imageURL) {
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


        /*ImageRequest request = new ImageRequest(imageURL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        userProfileView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        // Access the RequestQueue through your singleton class.
        RequestQueue.getInstance(this).addToRequestQueue(request);*/
    }

    public void loadUserNameView(String userName) {
        final TextView userNameView = (TextView) findViewById(R.id.userProfileNameView);
        userNameView.setText(userName);

    }

    @Override
    public void onLoggedOut() {
        Log.d("HomeScreenActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(int i) {
        Log.d("HomeScreenActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("HomeScreenActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("HomeScreenActivity", "Received connection message: " + message);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
