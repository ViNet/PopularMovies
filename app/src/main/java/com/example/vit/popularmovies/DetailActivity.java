package com.example.vit.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.parceler.Parcels;

import com.example.vit.popularmovies.rest.model.Movie;

/**
 * Created by Vit on 2015-07-17.
 */
public class DetailActivity extends AppCompatActivity {

    static final String CLASS = DetailActivity.class.getSimpleName() + ":";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie movie = Parcels.unwrap(getIntent().
                getParcelableExtra(com.example.vit.popularmovies.rest.model.Movie.class.getSimpleName()));
        Log.d(MovieApplication.TAG, CLASS + "onCreate() " + movie.getTitle());
    }
}
