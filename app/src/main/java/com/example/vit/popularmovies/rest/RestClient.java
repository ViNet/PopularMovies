package com.example.vit.popularmovies.rest;

import android.util.Log;

import com.example.vit.popularmovies.DataController;
import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.rest.conf.ApiConfig;
import com.example.vit.popularmovies.rest.model.DetailedMovie;
import com.example.vit.popularmovies.rest.model.Page;
import com.example.vit.popularmovies.rest.model.TrailersResult;
import com.example.vit.popularmovies.rest.service.ApiService;
import com.example.vit.popularmovies.utils.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class RestClient {
    static final String CLASS = RestClient.class.getSimpleName() + ": ";
    private static final String BASE_URL = "http://api.themoviedb.org/3";

    private static RestClient restClient;
    private ApiService apiService;
    private Bus bus;

    public static RestClient getInstance() {
        if (restClient == null) {
            restClient = new RestClient();
        }
        return restClient;
    }

    private RestClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }

    public void loadMovies(Map<String, String> options) {
        if (NetworkUtil.getConnectivityStatus(MovieApplication.getContext()) != NetworkUtil.TYPE_NOT_CONNECTED) {
            apiService.getMovies(options, ApiConfig.API_KEY, new Callback<Page>() {
                @Override
                public void success(Page page, Response response) {
                    Log.d(MovieApplication.TAG, CLASS + "loadMovies.success()");
                    DataController.getInstance().onLoadedMovies(page);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(MovieApplication.TAG, CLASS + "loadMovies.failure() error - " + error.toString());
                }
            });
        } else {
            DataController.getInstance().onNoInternet();
        }
    }

    public void loadDetailMovie(int movieId) {
        if (NetworkUtil.getConnectivityStatus(MovieApplication.getContext()) != NetworkUtil.TYPE_NOT_CONNECTED) {
            apiService.getDetailedMovie(movieId, ApiConfig.API_KEY, new Callback<DetailedMovie>() {
                @Override
                public void success(DetailedMovie detailedMovie, Response response) {
                    Log.d(MovieApplication.TAG, CLASS + "loadDetailedMovie.success()");
                    DataController.getInstance().onLoadedDetailedMovie(detailedMovie);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(MovieApplication.TAG, CLASS + "loadDetailedMovie.failure() error - " + error.toString());
                }
            });
        } else {
            DataController.getInstance().onNoInternet();
        }
    }

    public void loadTrailers(int movieId) {
        if (NetworkUtil.getConnectivityStatus(MovieApplication.getContext()) != NetworkUtil.TYPE_NOT_CONNECTED) {
            apiService.getVideosByMovieId(movieId, ApiConfig.API_KEY, new Callback<TrailersResult>() {
                @Override
                public void success(TrailersResult trailersResult, Response response) {
                    Log.d(MovieApplication.TAG, CLASS + "loadTrailers.success()");
                    DataController.getInstance().onLoadedTrailers(trailersResult);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(MovieApplication.TAG, CLASS + "loadTrailers.failure() error - " + error.toString());
                }
            });
        } else {
            DataController.getInstance().onNoInternet();
        }
    }

}