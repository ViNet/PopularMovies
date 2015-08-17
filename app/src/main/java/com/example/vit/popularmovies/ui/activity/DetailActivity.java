package com.example.vit.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.parceler.Parcels;

import com.example.vit.popularmovies.ExtraName;
import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.ui.fragment.MovieDetailFragment;
import com.example.vit.popularmovies.ui.fragment.MoviesFragment;


public class DetailActivity extends AppCompatActivity {

    static final String CLASS = DetailActivity.class.getSimpleName() + ":";
    MovieDetailFragment movieDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initToolbar();
        startFragment();
    }

    private void startFragment(){
        movieDetailFragment = (MovieDetailFragment)
                getFragmentManager().findFragmentById(R.id.detail_container);
        if(movieDetailFragment == null){
            movieDetailFragment =
                    MovieDetailFragment.newInstance();
            getFragmentManager().beginTransaction().
                    replace(R.id.detail_container, movieDetailFragment).commit();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }
    }
}
