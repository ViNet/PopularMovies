package com.example.vit.popularmovies.rest.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;


/**
 * Created by Vit on 26.06.2015.
 */
@Parcel
public class Movie{

    Integer id;
    Boolean adult;
    @SerializedName("backdrop_path")
        String backdropPath;
    @SerializedName("genre_ids")
    Integer[] genreIds;
    @SerializedName("original_language")
    String originalLanguage;
    @SerializedName("original_title")
    String originalTitle;
    String overview;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("poster_path")
    String posterPath;
    Double popularity;
    String title;
    Boolean video;
    @SerializedName("vote_average")
    Double voteAverage;
    @SerializedName("vote_count")
    Integer voteCount;


    public Movie() {
    }

    public Integer getId() {
        return this.id;
    }

    public Boolean getAdult() {
        return this.adult;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public Integer[] getGenreIds() {
        return this.genreIds;
    }

    public String getOriginalLanguage() {
        return this.originalLanguage;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getReleaseDate() {
        return this.releaseDate;
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

    public Boolean getVideo() {
        return this.video;
    }

    public Double getVoteAverage() {
        return this.voteAverage;
    }

    public Integer getVoteCount() {
        return this.voteCount;
    }



}
