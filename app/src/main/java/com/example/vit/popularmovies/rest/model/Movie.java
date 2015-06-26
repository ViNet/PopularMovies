package com.example.vit.popularmovies.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vit on 26.06.2015.
 */
public class Movie {

    private Integer id;
    private Boolean adult;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("genre_ids")
    private Integer[] genreIds;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("poster_path")
    private String posterPath;
    private Double popularity;
    private String title;
    private Boolean video;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("vote_count")
    private Integer voteCount;


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
