package com.example.vit.popularmovies;

import android.provider.ContactsContract;
import android.util.Log;

import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.Event;
import com.example.vit.popularmovies.communication.EventMessenger;
import com.example.vit.popularmovies.communication.NetEvents;
import com.example.vit.popularmovies.rest.RestClient;
import com.example.vit.popularmovies.rest.conf.ApiConfig;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.rest.model.Page;
import com.example.vit.popularmovies.utils.ApiUrlBuilder;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for all data loaded from api
 */
public class DataController {

    static final String CLASS = DataController.class.getSimpleName() + ": ";

    private static DataController dataController;

    // app's data cache
    private List<Movie> movies;
    private int pageId = ApiConfig.START_PAGE_ID;


    public static DataController getInstance(){
        if(dataController == null){
            dataController = new DataController();
        }
        return dataController;
    }

    public void loadMovies(){
        if(this.movies == null){
            // load from internet
            RestClient.getInstance().loadMovies(ApiUrlBuilder.buildGetMoviesOptions(pageId));
            Log.d(MovieApplication.TAG, CLASS + ApiUrlBuilder.buildGetMoviesOptions(pageId));
        } else {
            // load from cache
            EventMessenger.sendEvent(NetEvents.ON_DATA_AVAILABLE);
        }
    }

    public void onLoadedMovies(Page page){
        this.pageId = page.getPage();
        if(pageId == ApiConfig.START_PAGE_ID){
            // set new data
            this.movies = page.getMovies();
        } else {
            // add new data
            this.movies.addAll(page.getMovies());
        }
        EventMessenger.sendEvent(NetEvents.ON_DATA_AVAILABLE);
    }
}
