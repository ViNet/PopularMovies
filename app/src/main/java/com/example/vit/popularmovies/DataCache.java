package com.example.vit.popularmovies;

import com.example.vit.popularmovies.rest.conf.ApiConfig;
import com.example.vit.popularmovies.rest.model.Movie;

import java.util.List;

/**
 * Cache for a specific fragment
 * contains data loaded from API
 */
public class DataCache {

    private String owner;

    // app's data cache
    private List<Movie> movies;
    private List<Movie> nextMovies;     // next loaded page of movies
    private int pageId = ApiConfig.START_PAGE_ID;

    public DataCache(String owner){
        this.owner = owner;
    }

    public List<Movie> getMovies(){
        return this.movies;
    }

    public int getPageId(){
        return this.pageId;
    }

    public List<Movie> getNextMovies(){
        return this.nextMovies;
    }

    public void setPageId(int pageId){
        this.pageId = pageId;
    }

    public void setMovies(List<Movie> movies){
        this.movies = movies;
    }

    public void setNextMovies(List<Movie> movies){
        this.nextMovies = movies;
        addMovies(movies);
    }

    private void addMovies(List<Movie> movies){
        this.movies.addAll(movies);
    }

}
