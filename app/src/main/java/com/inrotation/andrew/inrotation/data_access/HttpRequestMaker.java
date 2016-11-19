package com.inrotation.andrew.inrotation.data_access;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Andrew on 11/17/16.
 */

public class HttpRequestMaker {

    static JSONObject HttpResponse = null;


    public static JSONObject makeGetRequest(String profileAccessUrl, final String accessToken, Context context, final VolleyCallback callback) {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject returnResponse;

        JsonObjectRequest arrayRequest = new JsonObjectRequest(Request.Method.GET, profileAccessUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        JSONObject tempResponse;
                        try {

                            JSONArray profilePic = response.getJSONArray("images");
                            String userName = response.getString("display_name");
                            JSONObject profilePicObject = profilePic.getJSONObject(0);
                            String profilePicURL = profilePicObject.getString("url");
                            Log.d("Profile Pic", profilePicURL);
                            //loadUserNameView(userName);
                            //loadProfilePic(profilePicURL);


                        }
                        catch (Exception e) {
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
                params.put("Authorization", "Bearer " + accessToken);
                //..add other headers
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(arrayRequest);

        return HttpResponse;
    }
}
