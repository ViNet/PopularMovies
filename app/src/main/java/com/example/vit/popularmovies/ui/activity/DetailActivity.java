package com.example.vit.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.ui.fragment.MovieDetailFragment;


public class DetailActivity extends AppCompatActivity {

    static final String CLASS = DetailActivity.class.getSimpleName() + ":";
    MovieDetailFragment movieDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
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

}
