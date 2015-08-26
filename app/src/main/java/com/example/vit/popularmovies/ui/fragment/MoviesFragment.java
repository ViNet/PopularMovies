package com.example.vit.popularmovies.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.vit.popularmovies.DataController;
import com.example.vit.popularmovies.ExtraName;
import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.NetEvents;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.ui.listener.EndlessRecyclerOnScrollListener;
import com.example.vit.popularmovies.ui.listener.RecyclerItemClickListener;
import com.example.vit.popularmovies.ui.activity.DetailActivity;
import com.example.vit.popularmovies.ui.adapter.MoviesAdapter;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Fragment with grid that contains movie posters
 */
public class MoviesFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener
        , EndlessRecyclerOnScrollListener.OnLoadMoreListener{

    private final static String CLASS = MoviesFragment.class.getSimpleName() + ": ";

    public static MoviesFragment instance;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private RelativeLayout noInternetView;
    private Button btnRetry;
    private ProgressBar pbLoading;
    private MoviesAdapter adapter;
    EndlessRecyclerOnScrollListener scrollListener;

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
                //Log.d(MovieApplication.TAG, CLASS + "ON_DATA_AVAILABLE");
                List<Movie> movies = DataController.getInstance().getMovies();
                if(movies.isEmpty()){
                    // movies on this request has been not found
                } else {
                    adapter.setData(movies);
                    // hide loading animation
                    showContentView();
                }
                break;
            case ON_LOAD_MORE_DATA_AVAILABLE:
                adapter.addData(DataController.getInstance().getNextMovies());
                break;
            case NO_INTERNET:
                if(adapter.getItemCount() == 0){
                    showNoInternetView();
                } else {
                    showNoInternetToast();
                    scrollListener.setIsLoadingNow(false);
                }
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d(MovieApplication.TAG, CLASS + "onCreateView()");
        view = inflater.inflate(R.layout.fragment_grid_movies, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // Log.d(MovieApplication.TAG, CLASS + "onViewCreated()");
        initViews();
        setupRecyclerView();
        setListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.d(MovieApplication.TAG, CLASS + "onStart()");
        BusProvider.getInstance().register(this);
        DataController.getInstance().loadMovies();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.d(MovieApplication.TAG, CLASS + "onPause()");
        BusProvider.getInstance().unregister(this);
    }

    private void initViews() {
        //Log.d(MovieApplication.TAG, CLASS + "initViews() ");
        recyclerView = (RecyclerView) view.findViewById(R.id.rvMovies);
        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
        noInternetView = (RelativeLayout) view.findViewById(R.id.noInternetView);
        btnRetry = (Button) view.findViewById(R.id.btnRetry);
    }

    private void setListeners() {
        //Log.d(MovieApplication.TAG, CLASS + "setListeners()");
        scrollListener =
                new EndlessRecyclerOnScrollListener(layoutManager, this);
        recyclerView.addOnScrollListener(scrollListener);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity().getBaseContext(), this));

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MovieApplication.TAG, CLASS + "retry");
                showLoadingView();
                DataController.getInstance().loadMovies();
            }
        });
    }

    private void setupRecyclerView() {
        //Log.d(MovieApplication.TAG, CLASS + "setupRecyclerView");
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

    private void hideContentView(){
        recyclerView.setVisibility(View.GONE);
    }

    private void hideNoInternetView(){
        noInternetView.setVisibility(View.GONE);
    }

    private void showNoInternetView(){
        hideContentView();
        hideLoadingView();
        noInternetView.setVisibility(View.VISIBLE);
        noInternetView.bringToFront();
    }

    private void showLoadingView(){
        hideContentView();
        hideNoInternetView();
        pbLoading.setVisibility(View.VISIBLE);
    }

    private void showContentView(){
        hideNoInternetView();
        hideLoadingView();
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showNoInternetToast(){
        Toast.makeText(
                getActivity().getBaseContext()
                , getString(R.string.no_network_connection)
                , Toast.LENGTH_SHORT)
                .show();
    }

    /*
            RECYCLER VIEW ON CLICK LISTENER
     */
    @Override
    public void onItemClick(View view, int position) {
        //Log.d(MovieApplication.TAG, CLASS + "on click pos = " + position);
        int movieId = adapter.getItemAtPosition(position).getId();
        Intent detailedActivityIntent = new Intent(getActivity(), DetailActivity.class);
        detailedActivityIntent.putExtra(ExtraName.MOVIE_ID, movieId);
        startActivity(detailedActivityIntent);
    }

    /*
        RECYCLER VIEW ON LOAD MORE LISTENER
    */
    @Override
    public void onLoadMore() {
        Log.d(MovieApplication.TAG, CLASS + "onLoadMore()");
        DataController.getInstance().loadMoreMovies();
    }
}
