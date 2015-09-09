package com.example.vit.popularmovies.utils;


import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.ui.fragment.FragmentTag;

import java.util.HashMap;
import java.util.Map;

public class ApiUrlBuilder {

    // Optional parameters keys
    public static final String SORT_BY = "sort_by";
    public static final String PAGE = "page";
    public static final String VOTE_COUNT = "vote_count.gte";

    public static final int MIN_VOTE_COUNT = 100;

    public static Map<String, String> buildGetMoviesOptions(int pageId, String customer){

        String sortBy = null;
        String minVoteCount = null;

        switch (customer){
            case FragmentTag.FR_POPULAR:
                sortBy = MovieApplication.getContext().getString(R.string.param_sort_by_popularity_desc);
                break;
            case FragmentTag.FR_MOST_VOTED:
                sortBy = MovieApplication.getContext().getString(R.string.param_sort_by_vote_count_desc);
                break;
            case FragmentTag.FR_TOP_RATED:
                sortBy = MovieApplication.getContext().getString(R.string.param_sort_by_vote_average_desc);
                minVoteCount = String.valueOf(MIN_VOTE_COUNT);
                break;
            default:
                break;
        }

        Map<String, String> options = new HashMap<>();
        options.put(SORT_BY, sortBy);
        options.put(PAGE, String.valueOf(pageId));
        options.put(VOTE_COUNT, minVoteCount);
        return options;
    }

    public static String buildImageUrl(String path, String size) {
        return "http://image.tmdb.org/t/p/" + size + "/" + path;
    }


    static public String buildYoutubeImageUrl(String key){
        return "http://img.youtube.com/vi/" + key + "/0.jpg";
    }
}
