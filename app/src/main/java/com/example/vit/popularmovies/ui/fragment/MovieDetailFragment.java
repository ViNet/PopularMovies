package com.example.vit.popularmovies.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.Event;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;


public class MovieDetailFragment extends Fragment {

    static final String CLASS = MovieDetailFragment.class.getSimpleName() + ": ";
    Bus bus = BusProvider.getInstance();

    public static MovieDetailFragment newInstance(int movieId){
        MovieDetailFragment fragment =  new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", movieId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        int id = args.getInt("id");

        Log.d(MovieApplication.TAG, CLASS + " id = " + id);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
        bus.post(new Event.LoadDetailedMovieEvent(getArguments().getInt("id")));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail ,container, false);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvDetailTitle);
        TextView tvYear = (TextView) view.findViewById(R.id.tvDetailYear);
        TextView tvRuntime = (TextView) view.findViewById(R.id.tvDetailRuntime);
        TextView tvRating = (TextView) view.findViewById(R.id.tvDetailRating);
        TextView tvOverview = (TextView) view.findViewById(R.id.tvDetailOverview);
        ImageView ivPoster = (ImageView) view.findViewById(R.id.ivDetailPoster);

        //tvTitle.setText(movie.getTitle());
        // getReleaseDate() returns date. Example 2013-10-08
        // so show only year
        //tvYear.setText(movie.getReleaseDate().substring(0,4));
        //tvRating.setText(movie.getVoteAverage().toString());
        //tvOverview.setText(movie.getOverview());
/*
        Picasso.with(getActivity().getBaseContext()).load(buildUrl(movie.getPosterPath()))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(ivPoster);
*/
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe
    public void onLoadedDetailedMovieEvent(Event.LoadedDetailedMovieEvent event){
        Log.d(MovieApplication.TAG, CLASS + "loaded = " + event.getDetailedMovie().getOriginalTitle());
    }

    public String buildUrl(String posterPath) {
        final String size = "w185";
        return "http://image.tmdb.org/t/p/" + size + "/" + posterPath;
    }
}
