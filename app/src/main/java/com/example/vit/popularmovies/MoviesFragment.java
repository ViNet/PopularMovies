package com.example.vit.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.Event;
import com.example.vit.popularmovies.rest.model.Movie;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vit on 2015-07-07.
 */
public class MoviesFragment extends Fragment {

    private RecyclerView rvMoviesGrid;
    private RecyclerView.LayoutManager layoutManager;
    private MoviesAdapter adapter;
    private List<Movie> moviesList = new ArrayList<Movie>();
    private Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = BusProvider.getInstance();
        //bus = ((MovieApplication)getActivity().getApplication()).getBus();
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
        bus.post(new Event.LoadMoviesEvent());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        rvMoviesGrid = (RecyclerView) view.findViewById(R.id.rvMoviesGrid);
        // use a grid layout manager
        layoutManager = new GridLayoutManager(getActivity().getBaseContext(), 2);
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
}
