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

import com.example.vit.popularmovies.ExtraName;
import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.Event;
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

    public static MovieDetailFragment newInstance(){
        if(instance == null){
            instance = new MovieDetailFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d(MovieApplication.TAG, CLASS + "onCreateView()");
        view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        return view;
    }
}
