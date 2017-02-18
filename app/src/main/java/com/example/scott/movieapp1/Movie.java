package com.example.scott.movieapp1;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    String mTitle;
    String mOverview;
    float mVoteAverage;
    String mReleaseDate;
    String mPosterUrl;

    public Movie(String mTitle, String mOverview, float mVoteAverage, String mReleaseDate, String mPosterUrl) {

        this.mTitle = mTitle;
        this.mOverview = mOverview;
        this.mVoteAverage = mVoteAverage;
        this.mReleaseDate = mReleaseDate;
        this.mPosterUrl = mPosterUrl;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getOverview(){
        return mOverview;
    }

    public float getVoteAverage(){
        return mVoteAverage;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public String getPosterUrl(){
        return mPosterUrl;
    }



    // Parcelable Methods
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTitle);
        out.writeString(mOverview);
        out.writeFloat(mVoteAverage);
        out.writeString(mReleaseDate);
        out.writeString(mPosterUrl);
    }

    public Movie(Parcel in) {
        mTitle = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readFloat();
        mReleaseDate = in.readString();
        mPosterUrl = in.readString();
    }

    public int describeContents() {
        return 0;
    }

}
