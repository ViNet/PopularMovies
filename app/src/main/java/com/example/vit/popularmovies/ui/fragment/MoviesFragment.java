package com.example.vit.popularmovies.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.vit.popularmovies.DataController;
import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.NetEvents;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.ui.EndlessRecyclerOnScrollListener;
import com.example.vit.popularmovies.ui.RecyclerItemClickListener;
import com.example.vit.popularmovies.ui.adapter.MoviesAdapter;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Fragment with grid that contains movie posters
 */
public class MoviesFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {

    private final static String CLASS = MoviesFragment.class.getSimpleName() + ": ";

    public static MoviesFragment instance;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private LinearLayout llContent;
    private ProgressBar pbLoading;
    private MoviesAdapter adapter;

    private View view;

    public static MoviesFragment getInstance() {
        if (instance == null) {
            instance = new MoviesFragment();
        }
        return instance;
    }

    @Subscribe
    public void onEvent(NetEvents event) {
        switch (event){
            case ON_DATA_AVAILABLE:
                Log.d(MovieApplication.TAG, CLASS + "ON_DATA_AVAILABLE");
                List<Movie> movies = DataController.getInstance().getMovies();
                if(movies.size() == 0){
                    // movies on this request has been found
                } else {
                    adapter.setData(movies);
                    // hide loading animation
                    hideLoadingView();
                }
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(MovieApplication.TAG, CLASS + "onCreateView()");
        view = inflater.inflate(R.layout.fragment_grid_movies, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(MovieApplication.TAG, CLASS + "onViewCreated()");
        initViews();
        setupRecyclerView();
        setListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MovieApplication.TAG, CLASS + "onStart()");
        BusProvider.getInstance().register(this);
        DataController.getInstance().loadMovies();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MovieApplication.TAG, CLASS + "onPause()");
        BusProvider.getInstance().unregister(this);
    }

    private void initViews() {
        Log.d(MovieApplication.TAG, CLASS + "initViews() ");
        recyclerView = (RecyclerView) view.findViewById(R.id.rvMovies);
        llContent= (LinearLayout) view.findViewById(R.id.llContent);
        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
    }

    private void setListeners() {
        Log.d(MovieApplication.TAG, CLASS + "setListeners()");
        // 20 - items per one page loaded from api
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager, 20) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d(MovieApplication.TAG, CLASS + "onLoadMore() page = " + currentPage);
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity().getBaseContext(), this));
    }

    private void setupRecyclerView() {
        Log.d(MovieApplication.TAG, CLASS + "setupRecyclerView");
        // use a grid layout manager
        layoutManager = new GridLayoutManager(getActivity().getBaseContext(), 2);

        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter
        adapter = new MoviesAdapter(getActivity().getBaseContext());
        recyclerView.setAdapter(adapter);
    }

    // hide progress bar
    private void hideLoadingView(){
        pbLoading.setVisibility(View.GONE);
    }

    /*
            RECYCLER VIEW ON CLICK LISTENER
     */
    @Override
    public void onItemClick(View view, int position) {
        Log.d(MovieApplication.TAG, CLASS + "on click pos = " + position);
    }
}
