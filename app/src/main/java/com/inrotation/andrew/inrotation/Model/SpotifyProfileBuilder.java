package com.inrotation.andrew.inrotation.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
            if (userName.isEmpty()) {
                userName = response.getString("id");
                if (userName.isEmpty()) {
                    userName = "No Name Available";
                }
            }
            String profileEmail = response.getString("email");
            String dbPassword = response.getString("birthdate");
            String profilePicURL = "";
            if (profilePic.length() != 0) {
                JSONObject profilePicObject = profilePic.getJSONObject(0);
                profilePicURL = profilePicObject.getString("url");
            }
            newUser = new HostUser(userName, spotifyAccessInstance.getAccessToken(), profilePicURL, profileEmail, dbPassword);
            newUser.setPlaylistToken(profileEmail.split("@")[0]);
            spotifyAccessInstance.setSpotifyUser(newUser);


        }
        catch (JSONException e) {
            throw new MyJSONException("Something went wrong");

        }

        return newUser;
    }

}
