package com.example.vit.popularmovies.rest.model;

import org.parceler.Parcel;

/**
 * "genres":[{"id":12,"name":"Adventure"},{"id":28,"name":"Action"},
 * {"id":53,"name":"Thriller"},{"id":878,"name":"Science Fiction"}]
 */
@Parcel
public class Genre {

    private Integer id;
    private String name;

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

}
