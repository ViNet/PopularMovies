package com.example.vit.popularmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.ui.fragment.MoviesFragment;


public class MainActivity extends AppCompatActivity {

    static final String CLASS = MainActivity.class.getSimpleName() + ": ";

    private MoviesFragment moviesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(MovieApplication.TAG, CLASS + "onCreate()");
        setContentView(R.layout.activity_main);
        initToolbar();
        //startFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void startFragment() {
        //Log.d(MovieApplication.TAG, CLASS + "startFragment()");
        /*
        moviesFragment = (MoviesFragment) getFragmentManager().findFragmentById(R.id.movies_container);
        if (moviesFragment == null) {
            //Log.d(MovieApplication.TAG, CLASS + "create new instance of movie fragment");
            moviesFragment = MoviesFragment.getInstance();
            getFragmentManager().beginTransaction().
                    replace(R.id.movies_container, moviesFragment).commit();
        }
        */
    }
}
