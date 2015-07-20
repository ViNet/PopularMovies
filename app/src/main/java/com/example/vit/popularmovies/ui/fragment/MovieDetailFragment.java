package com.example.vit.popularmovies.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;

import org.parceler.Parcels;

/**
 * Created by Vit on 2015-07-20.
 */
public class MovieDetailFragment extends Fragment {

    static final String CLASS = MovieDetailFragment.class.getSimpleName() + ": ";

    public static MovieDetailFragment newInstance(Bundle bundle){
        MovieDetailFragment fragment =  new MovieDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        com.example.vit.popularmovies.rest.model.Movie movie = Parcels.unwrap(
                args.getParcelable(com.example.vit.popularmovies.rest.model.Movie.class.getSimpleName()));

        Log.d(MovieApplication.TAG, CLASS  + "title " + movie.getOriginalTitle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail ,container, false);
    }
}
