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

public class DisplayMovieInfo extends AppCompatActivity {

    Context mContext;

    Movie movieInfo;

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

        movieInfo = getIntent().getExtras().getParcelable("Movie Data");

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

        Uri posterUri = Uri.parse(movieInfo.getPosterUrl());
        Picasso.with(mContext).load(posterUri).into(mPoster_view);

        mTitle_view.setText(movieInfo.getTitle());
        mVote_average_view.setRating((movieInfo.getVoteAverage()/2));
        mRelease_date_view.setText(movieInfo.getReleaseDate());
        mOverview_view.setText(movieInfo.getOverview());
    }

}
