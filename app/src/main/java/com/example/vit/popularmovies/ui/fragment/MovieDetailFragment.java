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
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;

/**
 * Created by Vit on 2015-07-20.
 */
public class MovieDetailFragment extends Fragment {

    static final String CLASS = MovieDetailFragment.class.getSimpleName() + ": ";
    com.example.vit.popularmovies.rest.model.Movie movie;

    public static MovieDetailFragment newInstance(Bundle bundle){
        MovieDetailFragment fragment =  new MovieDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        movie = Parcels.unwrap(
                args.getParcelable(com.example.vit.popularmovies.rest.model.Movie.class.getSimpleName()));

        Log.d(MovieApplication.TAG, CLASS + "title " + movie.getOriginalTitle());
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

        tvTitle.setText(movie.getTitle());
        // getReleaseDate() returns date. Example 2013-10-08
        // so show only year
        tvYear.setText(movie.getReleaseDate().substring(0,4));
        tvRating.setText(movie.getVoteAverage().toString());
        tvOverview.setText(movie.getOverview());

        Picasso.with(getActivity().getBaseContext()).load(buildUrl(movie.getPosterPath()))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(ivPoster);

        return view;
    }

    public String buildUrl(String posterPath) {
        final String size = "w185";
        return "http://image.tmdb.org/t/p/" + size + "/" + posterPath;
    }
}
