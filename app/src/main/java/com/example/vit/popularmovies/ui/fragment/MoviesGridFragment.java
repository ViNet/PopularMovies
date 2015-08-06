package com.example.vit.popularmovies.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.rest.model.Page;
import com.example.vit.popularmovies.ui.EndlessRecyclerOnScrollListener;
import com.example.vit.popularmovies.ui.RecyclerItemClickListener;
import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.Event;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.ui.adapter.MoviesAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fragment with grid that contains movie posters
 */

public class MoviesGridFragment extends Fragment implements
        RecyclerItemClickListener.OnItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener{

    static final String CLASS = MoviesGridFragment.class.getSimpleName() + ": ";

    private RecyclerView rvMoviesGrid;
    private GridLayoutManager layoutManager;
    private MoviesAdapter adapter;
    private List<Movie> moviesList = new ArrayList<>();
    private Bus bus;

    private boolean newQueryOptions = false;    // true, if settings changed

    // keys for bundle
    static final String KEY_MOVIES_LIST = "moviesList";

    public static MoviesGridFragment newInstance(){
        return new MoviesGridFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(MovieApplication.TAG, CLASS + "onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MovieApplication.TAG, CLASS + "onCreate()");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        settings.registerOnSharedPreferenceChangeListener(this);

        bus = BusProvider.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(MovieApplication.TAG, CLASS + "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_grid_movies, container, false);

        rvMoviesGrid = (RecyclerView) view.findViewById(R.id.rvMoviesGrid);
        // use a grid layout manager
        layoutManager = new GridLayoutManager(getActivity().getBaseContext(), 2);
        rvMoviesGrid.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity().getBaseContext(), this));
        rvMoviesGrid.setLayoutManager(layoutManager);
        rvMoviesGrid.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager, 20) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d(MovieApplication.TAG, CLASS + "onLoadMore() page = " + currentPage);
                loadMoviesPage(currentPage);
            }
        });
        // specify an adapter
        adapter = new MoviesAdapter(getActivity().getBaseContext(), moviesList);
        rvMoviesGrid.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(MovieApplication.TAG, CLASS + "onActivityCreated()");

        // restore previous dataset
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(KEY_MOVIES_LIST)){
                moviesList = Parcels.unwrap(savedInstanceState.getParcelable(KEY_MOVIES_LIST));
                adapter.setData(moviesList);
            }
        }
        Log.d(MovieApplication.TAG, CLASS + "movieList size = " + moviesList.size());
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
        // happens  at first start or when settings was changed
        if(moviesList.isEmpty() || newQueryOptions){
            newQueryOptions = false;
            //load first page
            loadMoviesPage(1);
        }

        Log.d(MovieApplication.TAG, CLASS + "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MovieApplication.TAG, CLASS + "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MovieApplication.TAG, CLASS + "onPause()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_MOVIES_LIST, Parcels.wrap(moviesList));
        Log.d(MovieApplication.TAG, CLASS + "onSaveInstanceState()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(MovieApplication.TAG, CLASS + "onStop()");
        bus.unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(MovieApplication.TAG, CLASS + "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MovieApplication.TAG, CLASS + "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MovieApplication.TAG, CLASS + "onDetach()");
    }

    @Subscribe
    public void onLoadedMoviesEvent(Event.LoadedMoviesEvent event){
        Page page = event.getPage();
        if(page.isStartingPage()){
            // set new data
            Log.d(MovieApplication.TAG, CLASS + "set new data");
            adapter.setData(page.getMovies());
        } else {
            // add new data to already existing data
            Log.d(MovieApplication.TAG, CLASS + "add new data ");
            adapter.addData(page.getMovies());
            this.moviesList.addAll(page.getMovies());
        }
        //Log.d(MovieApplication.TAG, CLASS + "movieList size = " + moviesList.size());
    }

    @Override
    public void onItemClick(View view, int position) {
        bus.post(new Event.ShowMovieDetail(moviesList.get(position)));
    }

    private void loadMoviesPage(int page){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        // make query options
        Map<String, String> options = new HashMap<>();
        options.put(Event.LoadMoviesEvent.SORT_BY, sharedPreferences.getString(getString(R.string.pref_order_key)
                , getString(R.string.param_sort_by_popularity_desc)));
        options.put(Event.LoadMoviesEvent.PAGE, String.valueOf(page));

        bus.post(new Event.LoadMoviesEvent(options));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(MovieApplication.TAG, CLASS + "onSharedPreferenceChanged()");
        newQueryOptions = true;

    }
}
