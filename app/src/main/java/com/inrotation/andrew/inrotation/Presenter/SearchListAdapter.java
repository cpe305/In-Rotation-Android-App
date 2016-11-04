package com.inrotation.andrew.inrotation.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.inrotation.andrew.inrotation.Model.AppSingleton;
import com.inrotation.andrew.inrotation.Model.Song;
import com.inrotation.andrew.inrotation.R;

import java.util.ArrayList;

import static com.inrotation.andrew.inrotation.R.layout.activity_new_playlist;

/**
 * Created by andrewcofano on 11/1/16.
 */

public class SearchListAdapter extends BaseAdapter {


    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Song> mDataSource;

    public SearchListAdapter(Context context, ArrayList<Song> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_song_search, parent, false);

        TextView songTitleTextView =
                (TextView) rowView.findViewById(R.id.songNameTextView);

        TextView artistTextView =
                (TextView) rowView.findViewById(R.id.songArtistTextView);

        final ImageView albumCoverImgView =
                (ImageView) rowView.findViewById(R.id.albumCoverImgView);

        Song song = (Song) getItem(position);

        songTitleTextView.setText(song.songName);
        artistTextView.setText(song.artists);

        ImageRequest request = new ImageRequest(song.albumCoverURLs.get(2),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        albumCoverImgView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        // Access the RequestQueue through your singleton class.
        AppSingleton.getInstance(mContext).addToRequestQueue(request);;


        return rowView;
    }


    @Override
    public int getCount() {
        return mDataSource.size();
    }


    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
