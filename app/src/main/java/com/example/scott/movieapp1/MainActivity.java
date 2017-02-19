package com.example.scott.movieapp1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.scott.movieapp1.Utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movieList = new ArrayList<>();
    private MovieAdapter mAdapter;
    private Spinner mSpinner;
    private RecyclerView mRecyclerView;
    private String mSortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkViews();
        listMovies();
        setRecyclerView();

    }

    private void linkViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mSpinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sort_order, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        Button submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMovies();
            }
        });
    }

    private void listMovies() {

        String BASE_URL = getString(R.string.movie_db_base_url);
        String API_KEY = getString(R.string.API_KEY_____);

        switch (mSpinner.getSelectedItem().toString()){
            case "Top Rated":
                mSortOrder = getString(R.string.sort_top_rated);
                break;
            case "Popular":
                mSortOrder = getString(R.string.sort_popular);
                break;
        }
        URL queryUri = NetworkUtils.buildUrl(BASE_URL,mSortOrder,API_KEY);
        new MovieDatabaseQuery().execute(queryUri);

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
            movieList.add(movie);

        }
    }

    private JSONArray parseJSON(String json) throws JSONException {

        JSONObject jObject = new JSONObject(json);
        JSONArray jArray = jObject.getJSONArray("results");

        return jArray;

    }

    public class MovieDatabaseQuery extends AsyncTask<URL, Void, String> {

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
                loopJsonArray(parseJSON(JSON));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setRecyclerView() {
        mAdapter = new MovieAdapter(this, movieList);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}