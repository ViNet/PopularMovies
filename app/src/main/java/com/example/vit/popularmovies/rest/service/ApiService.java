package com.example.vit.popularmovies.rest.service;

import com.example.vit.popularmovies.rest.model.DetailedMovie;
import com.example.vit.popularmovies.rest.model.Page;
import com.example.vit.popularmovies.rest.model.TrailersResult;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface ApiService {

    // get list of movie
    // path - /discover/movie?sort_by=popularity.desc
    @GET("/discover/movie")
    void getMovies(@Query("sort_by") String sortBy, @Query("api_key") String apiKey, Callback<Page> cb);

    // get full movie information by it's id
    // path - /movie/{id}
    @GET("/movie/{id}")
    void getDetailedMovie(@Path("id") int id, @Query("api_key") String apiKey, Callback<DetailedMovie> cb);

    // Get the videos (trailers, teasers, clips, etc...) for a specific movie id.
    // path - /movie/{id}/videos
    @GET("/movie/{id}/videos")
    void getVideosByMovieId(@Path("id") int id, @Query("api_key") String apiKey, Callback<TrailersResult> cb);

}

