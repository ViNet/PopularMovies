package com.example.vit.popularmovies.utils;


import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.SharedPreferencesManager;
import com.example.vit.popularmovies.communication.Event;

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
}
