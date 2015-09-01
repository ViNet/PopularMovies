package com.example.vit.popularmovies.ui.fragment;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vit.popularmovies.DataController;
import com.example.vit.popularmovies.ExtraName;
import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.NetEvents;
import com.example.vit.popularmovies.rest.model.DetailedMovie;
import com.example.vit.popularmovies.ui.adapter.TrailersAdapter;
import com.example.vit.popularmovies.ui.listener.RecyclerItemClickListener;
import com.example.vit.popularmovies.utils.ApiUrlBuilder;
import com.example.vit.popularmovies.utils.DateUtils;
import com.example.vit.popularmovies.utils.TextFormatter;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;


public class MovieDetailFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {

    static final String CLASS = MovieDetailFragment.class.getSimpleName() + ": ";

    public static MovieDetailFragment instance;

    View view;

    CollapsingToolbarLayout collapsingToolbarLayout;

    LinearLayout containerMovieInfo;
    LinearLayout containerMovieTrailers;
    ProgressBar pbLoading;
    RelativeLayout noInternetView;

    ImageView ivCoverImage;

    // views of movie info container
    TextView tvMovieTagline;
    ImageView ivMoviePoster;
    TextView tvMovieRating;
    TextView tvMovieVoteCount;
    TextView tvMovieOverview;

    // views of movie details card
    TextView tvReleased;
    TextView tvRuntime;
    TextView tvGenres;
    TextView tvBudget;
    TextView tvRevenue;
    TextView tvLanguages;

    // views of movie trailers container
    RecyclerView rvTrailersList;

    // view of no internet view
    Button btnRetry;


    public static MovieDetailFragment newInstance() {
        if (instance == null) {
            instance = new MovieDetailFragment();
        }
        return instance;
    }

    @Subscribe
    public void onEvent(NetEvents event) {
        Log.d(MovieApplication.TAG, CLASS + "onEvent()");
        switch (event) {
            case ON_MOVIE_INFO_DATA_AVAILABLE:
                Log.d(MovieApplication.TAG, CLASS + "ON_MOVIE_INFO_DATA_AVAILABLE");
                fillInfoView();
                showInfoView();
                break;
            case ON_MOVIE_TRAILERS_DATA_AVAILABLE:
                Log.d(MovieApplication.TAG, CLASS + "ON_MOVIE_TRAILERS_DATA_AVAILABLE");
                setupRecyclerView();
                showTrailersView();
                break;
            case ON_MOVIE_TRAILERS_NO_DATA:
                Log.d(MovieApplication.TAG, CLASS + "ON_MOVIE_TRAILERS_NO_DATA");
                hideTrailersView();
                break;
            case NO_INTERNET:
                Log.d(MovieApplication.TAG, CLASS + "NO_INTERNET");
                showNoInternetView();
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
        setListeners();
        initCollapseToolbar();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.d(MovieApplication.TAG, CLASS + "onStart()");
        BusProvider.getInstance().register(this);
        int movieId = getActivity().getIntent().getIntExtra(ExtraName.MOVIE_ID, 0);
        DataController.getInstance().loadDetailedMovie(movieId);
        DataController.getInstance().loadTrailers(movieId);
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.d(MovieApplication.TAG, CLASS + "onPause()");
        BusProvider.getInstance().unregister(this);
    }

    private void initViews() {
        containerMovieInfo = (LinearLayout) view.findViewById(R.id.containerMovieInfo);
        containerMovieTrailers = (LinearLayout) view.findViewById(R.id.containerMovieTrailers);
        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
        noInternetView = (RelativeLayout) view.findViewById(R.id.noInternetView);

        ivCoverImage = (ImageView) view.findViewById(R.id.ivCoverImage);

        //movie info views
        tvMovieTagline = (TextView) containerMovieInfo.findViewById(R.id.tvDetailTagline);
        ivMoviePoster = (ImageView) containerMovieInfo.findViewById(R.id.ivDetailPoster);
        tvMovieRating = (TextView) containerMovieInfo.findViewById(R.id.tvDetailRating);
        tvMovieVoteCount = (TextView) containerMovieInfo.findViewById(R.id.tvDetailVoteCount);
        tvMovieOverview = (TextView) containerMovieInfo.findViewById(R.id.tvDetailOverview);

        // details card
        LinearLayout llDetailsCard = (LinearLayout) containerMovieInfo.findViewById(R.id.llDetailCard);
        tvReleased = (TextView) llDetailsCard.findViewById(R.id.tvDetailReleased);
        tvRuntime = (TextView) llDetailsCard.findViewById(R.id.tvDetailRuntime);
        tvGenres = (TextView) llDetailsCard.findViewById(R.id.tvDetailGenres);
        tvBudget = (TextView) llDetailsCard.findViewById(R.id.tvDetailBudget);
        tvRevenue = (TextView) llDetailsCard.findViewById(R.id.tvDetailRevenue);
        tvLanguages = (TextView) llDetailsCard.findViewById(R.id.tvDetailLanguages);

        // trailers views
        rvTrailersList = (RecyclerView) containerMovieTrailers.findViewById(R.id.rvTrailersList);

        // no internet views
        btnRetry = (Button) noInternetView.findViewById(R.id.btnRetry);
    }

    private void setListeners() {
        // listener for no internet view
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(MovieApplication.TAG, CLASS + "onRetryClick()");
                showLoadingView();
                int movieId = getActivity().getIntent().getIntExtra(ExtraName.MOVIE_ID, 0);
                DataController.getInstance().loadDetailedMovie(movieId);
                DataController.getInstance().loadTrailers(movieId);

            }
        });

        // listeners for trailers view
        rvTrailersList.addOnItemTouchListener
                (new RecyclerItemClickListener(getActivity().getBaseContext(), this));
    }

    private void setupRecyclerView() {
        //Log.d(MovieApplication.TAG, CLASS + "setupRecyclerView");
        // use a grid layout manager
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity().
                        getBaseContext(), LinearLayoutManager.HORIZONTAL, false);

        rvTrailersList.setLayoutManager(layoutManager);
        // specify an adapter
        TrailersAdapter adapter =
                new TrailersAdapter(getActivity().getBaseContext(), DataController.getInstance().getTrailersList());
        rvTrailersList.setAdapter(adapter);
    }

    private void initCollapseToolbar(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

        }
    }


    private void fillInfoView() {
        DetailedMovie movie = DataController.getInstance().getDetailedMovie();
        // set toolbar title
        collapsingToolbarLayout.setTitle(movie.getTitle());

        tvMovieTagline.setText(movie.getTagline());
        tvMovieRating.setText(String.valueOf(movie.getVoteAverage()));
        tvMovieVoteCount.setText(String.valueOf(String.format("%1$,.0f", movie.getVoteCount())));
        tvMovieOverview.setText(movie.getOverview());

        // details card
        tvReleased.setText(DateUtils.formatDate(movie.getReleaseDate()));
        tvRuntime.setText(getString(R.string.runtime_min, movie.getRuntime()));
        //tvGenres.setText("");
        tvBudget.setText(TextFormatter.getCurrency(movie.getBudget()));
        tvRevenue.setText(TextFormatter.getCurrency(movie.getRevenue()));
        tvLanguages.setText(movie.getSpokenLanguagesString());

        //Log.d(MovieApplication.TAG, CLASS + "poster size - " + getString(R.string.poster_image_size));
        //Log.d(MovieApplication.TAG, CLASS + "backdrop size - " + getString(R.string.backdrop_image_size));

        Picasso.with(getActivity().getBaseContext())
                .load(ApiUrlBuilder.buildImageUrl(movie.getPosterPath(), getString(R.string.poster_image_size)))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(ivMoviePoster);


        Picasso.with(getActivity().getBaseContext())
                .load(ApiUrlBuilder.buildImageUrl(movie.getBackdropPath(), getString(R.string.backdrop_image_size)))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(ivCoverImage);
    }


    private void hideLoadingView() {
        pbLoading.setVisibility(View.GONE);
    }

    private void hideInfoView() {
        containerMovieInfo.setVisibility(View.GONE);
    }

    private void hideTrailersView() {
        containerMovieTrailers.setVisibility(View.GONE);
    }

    private void hideNoInternetView() {
        noInternetView.setVisibility(View.GONE);
    }

    private void showLoadingView() {
        hideInfoView();
        hideTrailersView();
        hideNoInternetView();
        pbLoading.setVisibility(View.VISIBLE);
    }

    private void showInfoView() {
        hideLoadingView();
        containerMovieInfo.setVisibility(View.VISIBLE);
    }

    private void showTrailersView() {
        hideLoadingView();
        containerMovieTrailers.setVisibility(View.VISIBLE);
    }

    private void showNoInternetView() {
        hideLoadingView();
        hideTrailersView();
        hideInfoView();
        noInternetView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(MovieApplication.TAG, CLASS + " click on - " + position);
        watchYoutubeVideo(DataController.getInstance().getTrailersList().get(position).getKey());
    }

    private void watchYoutubeVideo(String key) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + key));
            startActivity(intent);
        }
    }
}
