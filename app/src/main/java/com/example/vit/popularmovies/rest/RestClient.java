package com.example.vit.popularmovies.rest;

import android.util.Log;

import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.communication.Event;
import com.example.vit.popularmovies.rest.conf.ApiConfig;
import com.example.vit.popularmovies.rest.model.DetailedMovie;
import com.example.vit.popularmovies.rest.model.Page;
import com.example.vit.popularmovies.rest.model.TrailersResult;
import com.example.vit.popularmovies.rest.service.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class RestClient {
    static final String CLASS = RestClient.class.getSimpleName() + ": ";
    private static final String BASE_URL = "http://api.themoviedb.org/3";
    private ApiService apiService;
    private Bus bus;

    public RestClient(Bus bus) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(ApiService.class);

        this.bus = bus;
        this.bus.register(this);
    }

    public ApiService getApiService() {
        return apiService;
    }

    @Subscribe
    public void onLoadMoviesEvent(Event.LoadMoviesEvent event){
        apiService.getMovies(event.getSortBy(), ApiConfig.API_KEY, new Callback<Page>() {
            @Override
            public void success(Page page, retrofit.client.Response response) {
                //Log.d(MovieApplication.TAG, CLASS + "success size = " + page.getMovies().size());
                bus.post(new Event.LoadedMoviesEvent(page.getMovies()));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(MovieApplication.TAG, CLASS + "failure error = " + error.getMessage());
            }
        });
    }

    @Subscribe
    public void onLoadDetailedMovieEvent(Event.LoadDetailedMovieEvent event){
        Log.d(MovieApplication.TAG, CLASS + " Try to load movie id = " + event.getId());
        apiService.getDetailedMovie(event.getId(), ApiConfig.API_KEY, new Callback<DetailedMovie>() {
            @Override
            public void success(DetailedMovie detailedMovie, Response response) {
                bus.post(new Event.LoadedDetailedMovieEvent(detailedMovie));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(MovieApplication.TAG, CLASS + "failure error = " + error.getMessage());
            }
        });
    }

    @Subscribe
    public void onLoadVideosEvent(Event.LoadVideosEvent event){
        Log.d(MovieApplication.TAG, CLASS + " Try to load videos for movie, id = " + event.getMovieId());

        apiService.getVideosByMovieId(event.getMovieId(), ApiConfig.API_KEY, new Callback<TrailersResult>() {
            @Override
            public void success(TrailersResult trailersResult, Response response) {
                bus.post(new Event.LoadedVideosEvent(trailersResult.getTrailers()));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(MovieApplication.TAG, CLASS + "failure error = " + error.getMessage());
            }
        });
    }
}
