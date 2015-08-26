package com.example.vit.popularmovies.utils;


import java.util.HashMap;
import java.util.Map;

public class ApiUrlBuilder {

    // Optional parameters keys
    public static final String SORT_BY = "sort_by";
    public static final String PAGE = "page";

    public static Map<String, String> buildGetMoviesOptions(int pageId){
        Map<String, String> options = new HashMap<>();
        options.put(SORT_BY, SharedPreferencesManager.getPrefSortBy());
        options.put(PAGE, String.valueOf(pageId));
        return options;
    }

    public static String buildPosterUrl(String posterPath) {
        final String size = "w185";
        return "http://image.tmdb.org/t/p/" + size + "/" + posterPath;
    }


    static public String buildYoutubeImageUrl(String key){
        return "http://img.youtube.com/vi/" + key + "/0.jpg";
    }
}
