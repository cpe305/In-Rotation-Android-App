package com.inrotation.andrew.inrotation.model;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inrotation.andrew.inrotation.data_access.HttpRequestMaker;
import com.inrotation.andrew.inrotation.data_access.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static com.inrotation.andrew.inrotation.data_access.HttpRequestMaker.makeGetRequest;

/**
 * Created by andrewcofano on 11/8/16.
 */

public class SpotifyProfileBuilder {

    private static final String PROFILE_ACCESS_URL = "https://api.spotify.com/v1/me";

    public SpotifyProfileBuilder() {
    }

    public HostUser buildSpotifyProfile(JSONObject response) throws MyJSONException {

        SpotifyAccess spotifyAccessInstance = SpotifyAccess.getInstance();

        HostUser newUser = null;
        try {

            JSONArray profilePic = response.getJSONArray("images");
            String userName = response.getString("display_name");
            String profileEmail = response.getString("email");
            String dbPassword = response.getString("birthdate");
            JSONObject profilePicObject = profilePic.getJSONObject(0);
            String profilePicURL = profilePicObject.getString("url");
            newUser = new HostUser(userName, spotifyAccessInstance.getAccessToken(), profilePicURL, profileEmail, dbPassword);

            spotifyAccessInstance.setSpotifyUser(newUser);

        }
        catch (JSONException e) {
            throw new MyJSONException("Something went wrong");
        }

        return newUser;
    }

}
