package com.example.vit.popularmovies.rest.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;


@Parcel
public class Movie{

    Integer id;
    Boolean adult;
    @SerializedName("original_title")
    String originalTitle;
    @SerializedName("poster_path")
    String posterPath;
    Double popularity;
    String title;
    @SerializedName("vote_average")
    Double voteAverage;
    @SerializedName("vote_count")
    Integer voteCount;

    public static final int INVALID_MOVIE_ID = -1;

    public Movie() {
    }

    public Integer getId() {
        return this.id;
    }

    public Boolean getAdult() {
        return this.adult;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public Double getPopularity() {
        return this.popularity;
    }

    public String getTitle() {
        return this.title;
    }

    public Double getVoteAverage() {
        return this.voteAverage;
    }

    public Integer getVoteCount() {
        return this.voteCount;
    }



}
