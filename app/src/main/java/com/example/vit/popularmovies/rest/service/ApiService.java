package com.example.vit.popularmovies.rest.service;

import com.example.vit.popularmovies.rest.model.DetailedMovie;
import com.example.vit.popularmovies.rest.model.Page;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Vit on 26.06.2015.
 */


public interface ApiService {

    // get list of movie
    // /discover/movie?sort_by=popularity.desc&api_key={api_key}
    @GET("/discover/movie")
    void getMovies(@Query("sort_by") String sortBy, @Query("api_key") String apiKey, Callback<Page> cb);

    // get full movie information by it's id
    // /movie/{id}?api_key={api_key}
    @GET("/movie/{id}")
    void getDetailedMovie(@Path("id") int id, @Query("api_key") String apiKey, Callback<DetailedMovie> cb);
}

