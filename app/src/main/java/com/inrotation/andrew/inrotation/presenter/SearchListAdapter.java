package com.inrotation.andrew.inrotation.presenter;

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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.inrotation.andrew.inrotation.model.RequestQueue;
import com.inrotation.andrew.inrotation.model.Song;
import com.inrotation.andrew.inrotation.R;

import java.util.ArrayList;

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

        Song song = (Song) getItem(position);

        songTitleTextView.setText(song.songName);
        artistTextView.setText(song.artists);

        NetworkImageView mNetworkImageView;
        ImageLoader mImageLoader;

        // Get the NetworkImageView that will display the image.
        mNetworkImageView =(NetworkImageView) rowView.findViewById(R.id.albumCoverImgView);
        // Get the ImageLoader through your singleton class.
        mImageLoader = RequestQueue.getInstance(mContext).getImageLoader();
        // Set the URL of the image that should be loaded into this view, and
        // specify the ImageLoader that will be used to make the request.
        mNetworkImageView.setImageUrl(song.albumCoverURLs.get(0), mImageLoader);


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
