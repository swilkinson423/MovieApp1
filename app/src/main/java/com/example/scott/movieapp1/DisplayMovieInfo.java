package com.example.scott.movieapp1;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DisplayMovieInfo extends AppCompatActivity implements Parcelable {

    Context mContext;

    String mTitle;
    String mOverview;
    float mVoteAverage;
    String mReleaseDate;
    String mPosterUrl;

    ImageView mPoster_view;
    TextView mTitle_view;
    TextView mRelease_date_view;
    RatingBar mVote_average_view;
    TextView mOverview_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie_info);

        mContext = MyApplication.getAppContext();

        linkViews();
        setViews();

    }

    private void linkViews(){
        mPoster_view = (ImageView) findViewById(R.id.movie_poster);
        mTitle_view = (TextView) findViewById(R.id.movie_title);
        mRelease_date_view = (TextView) findViewById(R.id.movie_release_date);
        mVote_average_view = (RatingBar) findViewById(R.id.movie_vote_average);
        mOverview_view = (TextView) findViewById(R.id.movie_overview);
    }

    private void setViews(){
        Uri posterUri = Uri.parse(mPosterUrl);
        Picasso.with(mContext).load(posterUri).into(mPoster_view);

        mTitle_view.setText(mTitle);
        mVote_average_view.setRating(mVoteAverage);
        mRelease_date_view.setText(mReleaseDate);
        mOverview_view.setText(mOverview);
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
        out.writeDouble(mVoteAverage);
        out.writeString(mReleaseDate);
        out.writeString(mPosterUrl);
    }

    public DisplayMovieInfo(Parcel in) {
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
