package com.inrotation.andrew.inrotation.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Song implements Parcelable {

    public String songName;
    public  String artists;
    public String album;
    public int duration;
    public ArrayList<String> albumCoverURLs;
    public String songURI;
    public boolean isExplicit;



    public Song(String songName, String artists, String album, int duration, ArrayList<String> albumCoverURLs, String songURI, boolean isExplicit) {
        this.songName = songName;
        this.artists = artists;
        this.album = album;
        this.duration = duration;
        this.albumCoverURLs = albumCoverURLs;
        this.songURI = songURI;
        this.isExplicit = isExplicit;
    }


    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songName);
        dest.writeString(artists);
        dest.writeStringList(albumCoverURLs);
    }
}