package com.example.vit.popularmovies.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model for JSON response from themoviedb api
 * example:
 * {"id":87101,"results":[{"id":"552e46439251413f9c000592","iso_639_1":"en","key":"je73b_9JdR0",
 * "name":"Trailer 2","site":"YouTube","size":720,"type":"Trailer"},{"id":"5480c4d3c3a36829b20041f9"
 * ,"iso_639_1":"en","key":"62E4FJTwSuc","name":"Official Trailer","site":"YouTube","size":1080,
 * "type":"Trailer"}]}
 */
public class TrailersResult {
    Integer id;
    @SerializedName("results")
    List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return this.trailers;
    }

    public int getId(){
        return this.id;
    }
}
