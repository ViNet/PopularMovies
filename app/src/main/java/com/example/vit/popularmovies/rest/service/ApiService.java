package com.example.vit.popularmovies.rest.service;

import com.example.vit.popularmovies.rest.model.Page;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Vit on 26.06.2015.
 */


public interface ApiService {
    // /discover/movie?sort_by=popularity.desc&api_key=86f5e0c1cf67161dc2d836ebbbbb53d3
    @GET("/discover/movie")
    public void getMovie(@Query("sort_by") String sortBy, @Query("api_key") String apiKey, Callback<Page> cb);

}

