package com.example.vit.popularmovies.rest.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;


@Parcel
public class DetailedMovie {

    private Boolean adult;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private Double budget;
    private String homepage;
    private Integer id;
    @SerializedName("imdb_id")
    private String imdbId;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    private String overview;
    private Double popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("release_date")
    private String releaseDate;
    private Double revenue;
    private Integer runtime;
    private String status;
    private String tagline;
    private String title;
    private Boolean video;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("vote_count")
    private Double voteCount;

    @SerializedName("spoken_languages")
    private List<SpokenLanguages> spokenLanguagesList;

    public boolean getAdult(){
        return this.adult;
    }

    public String getBackdropPath(){
        return this.backdropPath;
    }

    public double getBudget(){
        return this.budget;
    }

    public String getHomepage(){
        return this.homepage;
    }

    public int getId(){
        return this.id;
    }

    public String getImdbId(){
        return this.imdbId;
    }

    public String getOriginalLanguage(){
        return this.originalLanguage;
    }

    public String getOriginalTitle(){
        return this.originalTitle;
    }

    public String getOverview(){
        return this.overview;
    }

    public double getPopularity(){
        return this.popularity;
    }

    public String getPosterPath(){
        return this.posterPath;
    }

    public String getReleaseDate(){
        return this.releaseDate;
    }

    public double getRevenue(){
        return this.revenue;
    }

    public int getRuntime(){
        return this.runtime;
    }

    public String getStatus(){
        return this.status;
    }

    public String getTagline(){
        return this.tagline;
    }

    public String getTitle(){
        return this.title;
    }

    public boolean getVideo(){
        return this.video;
    }

    public double getVoteAverage(){
        return this.voteAverage;
    }

    public double getVoteCount(){
        return this.voteCount;
    }

    public List<SpokenLanguages> getSpokenLanguages(){
        return this.spokenLanguagesList;
    }

    public String getSpokenLanguagesString(){
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        if(!this.spokenLanguagesList.isEmpty()){
            for(SpokenLanguages language : this.spokenLanguagesList){
                sb.append(prefix);
                sb.append(language.getName());
                prefix = ", ";
            }
        }
        return sb.toString();
    }



}
