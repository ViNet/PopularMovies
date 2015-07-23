package com.example.vit.popularmovies.rest.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Model for JSON response from themoviedb api
 * example:
 * {"id":"552e46439251413f9c000592","iso_639_1":"en","key":"je73b_9JdR0",
 * "name":"Trailer 2","site":"YouTube","size":720,"type":"Trailer"}
 */
@Parcel
public class Trailer {

    private String id;
    @SerializedName("iso_639_1")
    private String language;
    private String key;
    private String name;
    private String site;
    private Integer size;
    private String type;

    public String getId() {
        return this.id;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public String getSite() {
        return this.site;
    }

    public int getSize() {
        return this.size;
    }

    public String getType() {
        return type;
    }

}
