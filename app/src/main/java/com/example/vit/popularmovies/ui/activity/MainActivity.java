package com.example.vit.popularmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.Event;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.ui.fragment.MovieDetailFragment;
import com.example.vit.popularmovies.ui.fragment.MoviesGridFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;


public class MainActivity extends AppCompatActivity{

    static final String TAG = "PoMo";
    static final String CLASS = MainActivity.class.getSimpleName() + ": ";

    private MoviesGridFragment moviesFragment;
    private Bus bus;
    private boolean hasTwoPanes = false;
    private int selectedMovieId = Movie.INVALID_MOVIE_ID;
    private int selectedMoviePosition = RecyclerView.NO_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        moviesFragment = (MoviesGridFragment)
                getFragmentManager().findFragmentById(R.id.movies_container);
        if(moviesFragment == null){
            moviesFragment = MoviesGridFragment.newInstance();
            getFragmentManager().beginTransaction().
                    replace(R.id.movies_container, moviesFragment).commit();
        }

        initToolbar();

        bus = BusProvider.getInstance();

        hasTwoPanes = getResources().getBoolean(R.bool.has_two_panes);
        if(savedInstanceState != null){
            selectedMovieId = savedInstanceState.getInt("selectedMovieId", Movie.INVALID_MOVIE_ID);
            selectedMoviePosition = savedInstanceState.getInt("selectedMoviePosition", RecyclerView.NO_POSITION);
        }

        //Log.d(MovieApplication.TAG, CLASS + "has two panes - " + hasTwoPanes);
        //Log.d(MovieApplication.TAG, CLASS + "selected movie id - " + selectedMovieId);
        //Log.d(MovieApplication.TAG, CLASS + "selected movie pos - " + selectedMoviePosition);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);

        if(selectedMoviePosition != RecyclerView.NO_POSITION){
            moviesFragment.smoothScrollToPosition(selectedMoviePosition);
        }
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedMovieId", selectedMovieId);
        outState.putInt("selectedMoviePosition", selectedMoviePosition);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe
    public void onShowMovieDetailEvent(Event.ShowMovieDetail event){
        int moviePosition = event.getMoviePosition();
        int movieId = moviesFragment.getMovieIdByPosition(moviePosition);
        if(movieId != selectedMovieId || !hasTwoPanes){
            showDetails(movieId);
            selectedMovieId = movieId;
            selectedMoviePosition = moviePosition;
        }
    }

    @Subscribe
    public void onMoviesFragmentReady(Event.MoviesFragmentReady event){
        if(hasTwoPanes && selectedMovieId == Movie.INVALID_MOVIE_ID){
            // if there are no selected movie, show first item in list
            int movieId = moviesFragment.getMovieIdByPosition(0);
            if(movieId != Movie.INVALID_MOVIE_ID){
                showDetails(movieId);
                selectedMovieId = movieId;
                selectedMoviePosition = 0;
            }
        }
    }

    private void showDetails(int movieId){
        if(hasTwoPanes){
            //show details in fragment
            getFragmentManager().beginTransaction().
                    replace(R.id.detail_container, MovieDetailFragment.newInstance(movieId)).commit();
        } else {
            //show details in activity
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("id", movieId );
            startActivity(intent);
        }
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

}
