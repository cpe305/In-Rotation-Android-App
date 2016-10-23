package com.inrotation.andrew.inrotation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.VolleyError;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Metadata;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

public class HomeScreenActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    // TODO: Replace with your client ID
    private static final String CLIENT_ID = "11d0740d0e1442a59b1265510a5ba218";
    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "in-rotation://callback";

    private Player mPlayer;
    private String resAccessToken;

    private static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            resAccessToken = extras.getString("resAccessToken");
        }

        // if (response.getType() == AuthenticationResponse.Type.TOKEN) {
        Config playerConfig = new Config(this, resAccessToken, CLIENT_ID);
        Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
            @Override
            public void onInitialized(SpotifyPlayer spotifyPlayer) {
                mPlayer = spotifyPlayer;
                mPlayer.addConnectionStateCallback(HomeScreenActivity.this);
                mPlayer.addNotificationCallback(HomeScreenActivity.this);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("HomeScreenActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });


        // -- Test HTTP Req
        /*final Button httpReqButton = (Button) findViewById(R.id.httptest);
        final TextView httpResponse = (TextView) findViewById(R.id.responsetest);

        httpReqButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                testHTTP(httpResponse);
            }
        });*/


        /*final TextView mTextView = (TextView) findViewById(R.id.responsetest);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);*/
    }

    protected void testHTTP(TextView responseView) {
        HttpTransactionTest http = new HttpTransactionTest(responseView);
        try {
            responseView.setText(http.getQuote());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    /*
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        if (response.getType() == AuthenticationResponse.Type.TOKEN) {
            Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
            Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                @Override
                public void onInitialized(SpotifyPlayer spotifyPlayer) {
                    mPlayer = spotifyPlayer;
                    mPlayer.addConnectionStateCallback(HomeScreenActivity.this);
                    mPlayer.addNotificationCallback(HomeScreenActivity.this);

                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e("HomeScreenActivity", "Could not initialize player: " + throwable.getMessage());
                }
            });
        }

    }
*/
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

        mPlayer.playUri(null, "spotify:track:3gATNBVu8d7oWs9WijPDjD", 0, 0);

        final TextView mSongNameView = (TextView) findViewById(R.id.songNameView);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://www.google.com";
        Metadata metad = mPlayer.getMetadata();

       /* Log.d("HomeScreenActivity", url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mSongNameView.setText("Response is: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSongNameView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);*/

        /*while (mPlayer.getPlaybackState().isPlaying) {

           /* int load = R.drawable.loader;
            ImageView image = (ImageView) findViewById(R.id.albumCoverView);
            ImageLoader imgLoader = new ImageLoader(getApplicationContext());
            imgLoader.DisplayImage(url, loader, image);


            ImageView img = (ImageView) findViewById(R.id.albumCoverView);
            try {
                URL url = new URL(mPlayer.getMetadata().currentTrack.albumCoverWebUrl);
                //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
                HttpGet httpRequest = null;

                httpRequest = new HttpGet(url.toURI());

                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = (HttpResponse) httpclient
                        .execute(httpRequest);

                HttpEntity entity = response.getEntity();
                BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
                InputStream input = b_entity.getContent();

                Bitmap bitmap = BitmapFactory.decodeStream(input);

                img.setImageBitmap(bitmap);

            } catch (Exception ex) {

            }
        }*/
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
