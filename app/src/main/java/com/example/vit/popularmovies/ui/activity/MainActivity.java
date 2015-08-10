package com.example.vit.popularmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.Event;
import com.example.vit.popularmovies.ui.fragment.MoviesGridFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;


public class MainActivity extends AppCompatActivity{

    static final String TAG = "PoMo";
    static final String CLASS = MainActivity.class.getSimpleName() + ": ";

    private Bus bus;
    private boolean hasTwoPanes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        if(getFragmentManager().findFragmentById(R.id.movies_container) == null){
            getFragmentManager().beginTransaction().
                    replace(R.id.movies_container, MoviesGridFragment.newInstance()).commit();
        }

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bus = BusProvider.getInstance();
        hasTwoPanes = getResources().getBoolean(R.bool.has_two_panes);
        Log.d(MovieApplication.TAG, CLASS + "has two panes - " + hasTwoPanes);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe
    public void onShowMovieDetailEvent(Event.ShowMovieDetail event){
        Log.d(TAG, CLASS + "onShowMovieDetailEvent()"
                + " title = " + event.getMovie().getOriginalTitle());

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", event.getMovie().getId());
        startActivity(intent);
    }

}
