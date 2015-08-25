package com.example.vit.popularmovies.ui.fragment;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vit.popularmovies.DataController;
import com.example.vit.popularmovies.ExtraName;
import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.Event;
import com.example.vit.popularmovies.communication.NetEvents;
import com.example.vit.popularmovies.rest.model.DetailedMovie;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.rest.model.Trailer;
import com.example.vit.popularmovies.ui.RecyclerItemClickListener;
import com.example.vit.popularmovies.ui.adapter.TrailersAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;


public class MovieDetailFragment extends Fragment {

    static final String CLASS = MovieDetailFragment.class.getSimpleName() + ": ";

    public static MovieDetailFragment instance;

    View view;
    LinearLayout containerMovieInfo;
    LinearLayout containerMovieTrailers;
    ProgressBar pbLoading;

    public static MovieDetailFragment newInstance(){
        if(instance == null){
            instance = new MovieDetailFragment();
        }
        return instance;
    }

    @Subscribe
    public void onEvent(NetEvents event){
        Log.d(MovieApplication.TAG, CLASS + "onEvent()");
        switch (event){
            case ON_MOVIE_INFO_DATA_AVAILABLE:
                Log.d(MovieApplication.TAG, CLASS + "ON_MOVIE_INFO_DATA_AVAILABLE");
                //fillInfoView();
                //showInfoView();
                break;
            case ON_MOVIE_TRAILERS_DATA_AVAILABLE:
                Log.d(MovieApplication.TAG, CLASS + "ON_MOVIE_TRAILERS_DATA_AVAILABLE)");
                break;
            case NO_INTERNET:
                Log.d(MovieApplication.TAG, CLASS + "NO_INTERNET");
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d(MovieApplication.TAG, CLASS + "onCreateView()");
        view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        //setupRecyclerView();
        //setListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.d(MovieApplication.TAG, CLASS + "onStart()");
        BusProvider.getInstance().register(this);
        DataController.getInstance().loadDetailedMovie(getActivity().getIntent().getIntExtra(ExtraName.MOVIE_ID, 0));
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.d(MovieApplication.TAG, CLASS + "onPause()");
        BusProvider.getInstance().unregister(this);
    }

    private void initViews(){
        containerMovieInfo = (LinearLayout) view.findViewById(R.id.containerMovieInfo);
        containerMovieTrailers = (LinearLayout) view.findViewById(R.id.containerMovieTrailers);
        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
    }

    private void hideLoadingView(){
       pbLoading.setVisibility(View.GONE);
    }

    private void hideInfoView(){
        containerMovieInfo.setVisibility(View.GONE);
    }

    private void hideTrailersView(){
        containerMovieTrailers.setVisibility(View.GONE);
    }

    private void showLoadingView(){
        hideInfoView();
        hideTrailersView();
    }

    private void showInfoView(){
        hideLoadingView();
        containerMovieInfo.setVisibility(View.VISIBLE);
    }

    private void showTrailersView(){
        hideLoadingView();
        containerMovieTrailers.setVisibility(View.VISIBLE);
    }

    private void fillInfoView(){
    }

}
