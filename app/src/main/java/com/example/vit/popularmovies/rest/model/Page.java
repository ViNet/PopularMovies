package com.example.vit.popularmovies.rest.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Page {

    @SerializedName("page")
    private Integer pageId;
    @SerializedName("results")
    private List<Movie> movies;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;

    public Integer getPage() {
        return pageId;
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public Integer getTotalResults() {
        return this.totalResults;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public boolean isStartingPage(){
        return this.pageId == 1;
    }
}
