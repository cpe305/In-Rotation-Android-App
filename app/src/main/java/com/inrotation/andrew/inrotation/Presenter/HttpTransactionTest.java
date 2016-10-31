package com.inrotation.andrew.inrotation.Presenter;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.ImageLoader;

import android.os.AsyncTask;
import android.widget.TextView;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by brady on 10/20/16.
 */

// To use Volley, you must add android.permission.INTERNET to the permissions of the app manifest.

public class HttpTransactionTest extends AsyncTask<Object, Void, String> {
    private TextView responseView;

    public HttpTransactionTest(TextView responseView) {
        this.responseView = responseView;
    }

    public String getQuote() throws Throwable {
        String url = "http://www.android.com/";
        String[] urls = new String[2];
        urls[0] = url;
        String res = doInBackground(url);
        urls[1] = res;
        return url;
    }

    @Override
    protected String doInBackground(Object... objects) {
        String urlString = (String) objects[0];
        HttpURLConnection urlConnection = null;
        String res = "res";
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.d("HTTPTranscation", "yup");
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = in.read();
                while(i != -1) {
                    bo.write(i);
                    i = in.read();
                }
                res = bo.toString();
                Log.d("HTTPTranscation", res);
            } catch (IOException e) {
                res = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return res;
    };

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        this.responseView.setText(s);
    }

//    private String readStream(InputStream is) {
//        try {
//            ByteArrayOutputStream bo = new ByteArrayOutputStream();
//            int i = is.read();
//            while(i != -1) {
//                bo.write(i);
//                i = is.read();
//            }
//            return bo.toString();
//        } catch (IOException e) {
//            return "";
//        }
//    }

}




