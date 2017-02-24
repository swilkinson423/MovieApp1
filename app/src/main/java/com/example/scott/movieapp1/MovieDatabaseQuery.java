package com.example.scott.movieapp1;

import android.os.AsyncTask;

import com.example.scott.movieapp1.Utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

class MovieDatabaseQuery extends AsyncTask<URL, Void, String> {

    private MovieAdapter mAdapter;
    private ArrayList<Movie> mMovieList;

    MovieDatabaseQuery(ArrayList<Movie> movieList, MovieAdapter adapter){
        mAdapter = adapter;
        mMovieList = movieList;
    }

    @Override
    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
        String JSON = "";
        try {
            JSON = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON;
    }

    @Override
    protected void onPostExecute(String JSON) {
        try {
            if(mMovieList != null) mMovieList.clear();
            loopJsonArray(parseJSON(JSON));
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loopJsonArray(JSONArray jsonArray) throws JSONException {

        JSONObject currentMovie;

        String title;
        String overview;
        float vote_average;
        String release_date;

        String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
        String img_url;

        Movie movie;

        for (int i = 0; i < jsonArray.length(); i++) {

            currentMovie = jsonArray.getJSONObject(i);

            title = currentMovie.getString("original_title");
            overview = currentMovie.getString("overview");
            vote_average = (float) currentMovie.getDouble("vote_average");
            release_date = currentMovie.getString("release_date");

            img_url = IMAGE_URL + currentMovie.getString("poster_path");

            movie = new Movie(title, overview, vote_average, release_date, img_url);
            mMovieList.add(movie);

        }
    }

    private JSONArray parseJSON(String json) throws JSONException {

        JSONObject jObject = new JSONObject(json);
        return jObject.getJSONArray("results");

    }

}


