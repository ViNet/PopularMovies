package com.example.vit.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Vit on 2015-07-07.
 */
public class MoviesFragment extends Fragment {

    private RecyclerView rvMoviesGrid;
    private RecyclerView.LayoutManager layoutManager;
    private MoviesAdapter adapter;
    String[] dataset = {"Avatar", "Lord of the Rings", "Interstellar"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        rvMoviesGrid = (RecyclerView) view.findViewById(R.id.rvMoviesGrid);
        // use a grid layout manager
        layoutManager = new GridLayoutManager(getActivity().getBaseContext(), 2);
        rvMoviesGrid.setLayoutManager(layoutManager);

        // specify an adapter
        adapter = new MoviesAdapter(dataset);
        rvMoviesGrid.setAdapter(adapter);


        return view;
    }
}
