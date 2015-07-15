package com.example.vit.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.Event;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;


public class MainActivity extends AppCompatActivity{

    static final String TAG = "PoMo";
    static final String CLASS = MainActivity.class.getSimpleName() + ": ";

    private Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bus = BusProvider.getInstance();
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
    }

}
