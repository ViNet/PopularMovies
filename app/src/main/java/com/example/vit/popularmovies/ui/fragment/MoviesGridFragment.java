package com.example.vit.popularmovies.ui.fragment;

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
import com.example.vit.popularmovies.ui.RecyclerItemClickListener;
import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.Event;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.ui.adapter.MoviesAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vit on 2015-07-07.
 */
public class MoviesGridFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {

    static final String CLASS = MoviesGridFragment.class.getSimpleName() + ": ";

    private RecyclerView rvMoviesGrid;
    private RecyclerView.LayoutManager layoutManager;
    private MoviesAdapter adapter;
    private List<Movie> moviesList = new ArrayList<Movie>();
    private Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = BusProvider.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        Log.d(MovieApplication.TAG, CLASS + " settings, order by = "
                + sharedPreferences.getString(getString(R.string.pref_order_key)
                , getString(R.string.param_sort_by_popularity_desc)));

        bus.register(this);
        bus.post(new Event.LoadMoviesEvent(sharedPreferences.getString(getString(R.string.pref_order_key)
                , getString(R.string.param_sort_by_popularity_desc))));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        rvMoviesGrid = (RecyclerView) view.findViewById(R.id.rvMoviesGrid);
        // use a grid layout manager
        layoutManager = new GridLayoutManager(getActivity().getBaseContext(), 2);
        rvMoviesGrid.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity().getBaseContext(), this));
        rvMoviesGrid.setLayoutManager(layoutManager);

        // specify an adapter
        adapter = new MoviesAdapter(getActivity().getBaseContext(), moviesList);
        rvMoviesGrid.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe
    public void onLoadedMoviesEvent(Event.LoadedMoviesEvent event){
        adapter.setData(event.getMovieList());
    }


    @Override
    public void onItemClick(View view, int position) {
        bus.post(new Event.ShowMovieDetail(moviesList.get(position)));
    }
}
