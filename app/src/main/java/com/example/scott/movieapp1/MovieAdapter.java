package com.example.scott.movieapp1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> mMovieList;
    Context mContext;

    public MovieAdapter(Context context, ArrayList<Movie> movies){
        mMovieList = movies;
        mContext = context;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView mPoster;

        public MovieViewHolder(View view) {
            super(view);
            mPoster = (ImageView) view.findViewById(R.id.poster_image_view);
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_movie, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, final int position) {

        Uri posterUri = Uri.parse(mMovieList.get(position).getPosterUrl());
        Context context = MyApplication.getAppContext();
        Picasso.with(context).load(posterUri).into(movieViewHolder.mPoster);

        View.OnClickListener click = new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(mContext,DisplayMovieInfo.class);
                myIntent.putExtra("Movie Data", mMovieList.get(position));
                mContext.startActivity(myIntent);

            }
        };

        movieViewHolder.mPoster.setOnClickListener(click);

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }
}
