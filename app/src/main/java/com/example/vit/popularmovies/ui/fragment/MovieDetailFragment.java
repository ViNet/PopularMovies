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
import com.example.vit.popularmovies.rest.model.DetailedMovie;
import com.example.vit.popularmovies.rest.model.Trailer;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;


public class MovieDetailFragment extends Fragment {

    static final String CLASS = MovieDetailFragment.class.getSimpleName() + ": ";
    Bus bus = BusProvider.getInstance();

    TextView tvTitle;
    TextView tvYear;
    TextView tvRuntime;
    TextView tvRating;
    TextView tvOverview;
    ImageView ivPoster;

    DetailedMovie detailedMovie;
    List<Trailer> trailerList;

    public static MovieDetailFragment newInstance(int movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", movieId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MovieApplication.TAG, CLASS + "onCreate()");

        if (savedInstanceState != null) {
            this.detailedMovie = Parcels.
                    unwrap(savedInstanceState.getParcelable(DetailedMovie.class.getSimpleName()));
            this.trailerList = Parcels.
                    unwrap(savedInstanceState.getParcelable(Trailer.class.getSimpleName()));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(MovieApplication.TAG, CLASS + "onSaveInstanceState()");
        outState.putParcelable(DetailedMovie.class.getSimpleName(), Parcels.wrap(this.detailedMovie));
        Log.d(MovieApplication.TAG, CLASS + "trailerlist size = " + trailerList.size());
        outState.putParcelable(Trailer.class.getSimpleName(), Parcels.wrap(this.trailerList));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        tvTitle = (TextView) view.findViewById(R.id.tvDetailTitle);
        tvYear = (TextView) view.findViewById(R.id.tvDetailYear);
        tvRuntime = (TextView) view.findViewById(R.id.tvDetailRuntime);
        tvRating = (TextView) view.findViewById(R.id.tvDetailRating);
        tvOverview = (TextView) view.findViewById(R.id.tvDetailOverview);
        ivPoster = (ImageView) view.findViewById(R.id.ivDetailPoster);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);

        // if detailedMovie is null than loading data
        if (this.detailedMovie == null) {
            bus.post(new Event.LoadDetailedMovieEvent(getArguments().getInt("id")));
        }
        // if detailedMovie is not null than orientation was changed and we'll not load data again
        else {
            setData();
        }

        if (this.trailerList == null) {
            Log.d(MovieApplication.TAG, CLASS + "trailerList == null");
            bus.post(new Event.LoadVideosEvent(getArguments().getInt("id")));
        } else {
            // fill videos
            Log.d(MovieApplication.TAG, CLASS + "trailerList != null");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe
    public void onLoadedDetailedMovieEvent(Event.LoadedDetailedMovieEvent event) {
        this.detailedMovie = event.getDetailedMovie();
        //Log.d(MovieApplication.TAG, CLASS + "video: " + detailedMovie.getVideo());
        setData();
    }

    @Subscribe
    public void onLoadedVideosEvent(Event.LoadedVideosEvent event) {
        this.trailerList = event.getTrailerList();
        if (!trailerList.isEmpty()) {
            for (Trailer t : trailerList) {
                Log.d(MovieApplication.TAG, CLASS + "name - " + t.getName());
            }
        } else {
            Log.d(MovieApplication.TAG, CLASS + "Loaded video list is empty");
        }
    }

    public String buildUrl(String posterPath) {
        final String size = "w185";
        return "http://image.tmdb.org/t/p/" + size + "/" + posterPath;
    }

    private void setData() {
        tvTitle.setText(detailedMovie.getTitle());
        // getReleaseDate() returns date. Example 2013-10-08
        // so show only year
        tvYear.setText(detailedMovie.getReleaseDate().substring(0, 4));
        tvRuntime.setText(getString(R.string.runtime, String.valueOf(detailedMovie.getRuntime())));
        tvRating.setText(getString(R.string.rating, String.valueOf(detailedMovie.getVoteAverage())));
        tvOverview.setText(detailedMovie.getOverview());

        Picasso.with(getActivity().getBaseContext()).load(buildUrl(detailedMovie.getPosterPath()))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(ivPoster);
    }
}
