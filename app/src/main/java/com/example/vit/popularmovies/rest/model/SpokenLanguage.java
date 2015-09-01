package com.example.vit.popularmovies.rest.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

//"spoken_languages":[{"iso_639_1":"en","name":"English"}
@Parcel
public class SpokenLanguage {

    @SerializedName("iso_639_1")
    private String isoCode;
    private String name;

    public String getIsoCode(){
        return this.isoCode;
    }

    public String getName(){
        return this.name;
    }
}
