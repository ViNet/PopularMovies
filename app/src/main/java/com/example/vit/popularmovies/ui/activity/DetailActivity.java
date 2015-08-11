package com.example.vit.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.parceler.Parcels;

import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.ui.fragment.MovieDetailFragment;


public class DetailActivity extends AppCompatActivity {

    static final String CLASS = DetailActivity.class.getSimpleName() + ":";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getResources().getBoolean(R.bool.has_two_panes)){
            // close details activity if there are must be 2 panes layout
            finish();
            return;
        }

        if(getFragmentManager().findFragmentById(R.id.detail_container) == null){
            getFragmentManager().beginTransaction().
                    replace(R.id.detail_container, MovieDetailFragment.newInstance(getIntent().getIntExtra("id", 0))).commit();
        }

        initToolbar();
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
