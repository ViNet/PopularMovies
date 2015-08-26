package com.example.vit.popularmovies;

import android.util.Log;

import com.example.vit.popularmovies.communication.EventMessenger;
import com.example.vit.popularmovies.communication.NetEvents;
import com.example.vit.popularmovies.rest.RestClient;
import com.example.vit.popularmovies.rest.conf.ApiConfig;
import com.example.vit.popularmovies.rest.model.DetailedMovie;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.rest.model.Page;
import com.example.vit.popularmovies.rest.model.Trailer;
import com.example.vit.popularmovies.rest.model.TrailersResult;
import com.example.vit.popularmovies.utils.ApiUrlBuilder;

import java.util.List;

/**
 * Container for all data loaded from api
 */
public class DataController {

    static final String CLASS = DataController.class.getSimpleName() + ": ";

    private static DataController dataController;

    // app's data cache
    private List<Movie> movies;
    private List<Movie> nextMovies;     // next loaded page of movies
    private int pageId = ApiConfig.START_PAGE_ID;

    private DetailedMovie detailedMovie;
    private TrailersResult trailersResult;

    public static DataController getInstance() {
        if (dataController == null) {
            dataController = new DataController();
        }
        return dataController;
    }

    public void clearData() {
        this.movies = null;
        this.nextMovies = null;
        pageId = ApiConfig.START_PAGE_ID;
    }

    // CALLED FROM UI

    public void loadMovies() {
        if (this.movies == null) {
            // load from internet
            RestClient.getInstance().loadMovies(ApiUrlBuilder.buildGetMoviesOptions(pageId));
            //Log.d(MovieApplication.TAG, CLASS + ApiUrlBuilder.buildGetMoviesOptions(pageId));
        } else {
            // load from cache
            EventMessenger.sendEvent(NetEvents.ON_DATA_AVAILABLE);
        }
    }

    public void loadDetailedMovie(int movieId) {
        //Log.d(MovieApplication.TAG, CLASS + "loadDetailedMovie()");
        if (detailedMovie == null || detailedMovie.getId() != movieId) {
            // load from internet
            RestClient.getInstance().loadDetailMovie(movieId);
            //Log.d(MovieApplication.TAG, CLASS + "load from internet");
        } else {
            // load from cache
            EventMessenger.sendEvent(NetEvents.ON_MOVIE_INFO_DATA_AVAILABLE);
            //Log.d(MovieApplication.TAG, CLASS + "load from cache");
        }
    }

    public void loadMoreMovies() {
        RestClient.getInstance().loadMovies(ApiUrlBuilder.buildGetMoviesOptions(pageId + 1));
        Log.d(MovieApplication.TAG, CLASS + ApiUrlBuilder.buildGetMoviesOptions(pageId + 1));
    }

    public void loadTrailers(int movieId) {
        //Log.d(MovieApplication.TAG, CLASS + "loadTrailers()");
        if (trailersResult == null || trailersResult.getId() != movieId) {
            // load from internet
            //Log.d(MovieApplication.TAG, CLASS + "load from internet");
            RestClient.getInstance().loadTrailers(movieId);
        } else {
            if(trailersResult.getTrailers().isEmpty()){
                // trailers for this movie doesn't exist
                EventMessenger.sendEvent(NetEvents.ON_MOVIE_TRAILERS_NO_DATA);
            } else {
                //load from cache
                //Log.d(MovieApplication.TAG, CLASS + "load from cache");
                EventMessenger.sendEvent(NetEvents.ON_MOVIE_TRAILERS_DATA_AVAILABLE);
            }
        }
    }

    // CALLED FROM REST CLIENT

    public void onLoadedMovies(Page page) {
        this.pageId = page.getPage();
        if (pageId == ApiConfig.START_PAGE_ID) {
            // set new data
            this.movies = page.getMovies();
            EventMessenger.sendEvent(NetEvents.ON_DATA_AVAILABLE);
        } else if (pageId > ApiConfig.START_PAGE_ID) {
            // add new data
            this.nextMovies = page.getMovies();
            this.movies.addAll(nextMovies);
            EventMessenger.sendEvent(NetEvents.ON_LOAD_MORE_DATA_AVAILABLE);
        } else {
            // page is empty
        }
    }

    public void onLoadedDetailedMovie(DetailedMovie detailedMovie) {
        this.detailedMovie = detailedMovie;
        EventMessenger.sendEvent(NetEvents.ON_MOVIE_INFO_DATA_AVAILABLE);
    }

    public void onLoadedTrailers(TrailersResult trailersResult) {
        //Log.d(MovieApplication.TAG, CLASS + "onLoadedTrailers()");
        this.trailersResult = trailersResult;
        if (trailersResult.getTrailers().isEmpty()) {
            EventMessenger.sendEvent(NetEvents.ON_MOVIE_TRAILERS_NO_DATA);
        } else {
            EventMessenger.sendEvent(NetEvents.ON_MOVIE_TRAILERS_DATA_AVAILABLE);
        }
    }

    public void onNoInternet() {
        EventMessenger.sendEvent(NetEvents.NO_INTERNET);
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    public List<Movie> getNextMovies() {
        return this.nextMovies;
    }

    public DetailedMovie getDetailedMovie() {
        return this.detailedMovie;
    }

    public List<Trailer> getTrailersList() {
        //Log.d(MovieApplication.TAG, CLASS + "getTrailersList()");
        return this.trailersResult.getTrailers();
    }
}
