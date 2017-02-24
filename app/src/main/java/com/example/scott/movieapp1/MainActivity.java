package com.example.scott.movieapp1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.scott.movieapp1.Utilities.NetworkUtils;

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
        setRecyclerView();
        listMovies();

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

    private void setRecyclerView() {
        mAdapter = new MovieAdapter(this, movieList);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), calculateNoOfColumns(this));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
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
        new MovieDatabaseQuery(movieList, mAdapter).execute(queryUri);

    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

}