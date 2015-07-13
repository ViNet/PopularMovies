package com.example.vit.popularmovies;

import android.app.Application;
import android.util.Log;

import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.rest.RestClient;
import com.squareup.otto.Bus;

/**
 * Created by Vit on 2015-07-06.
 */
public class MovieApplication extends Application {

    public static final String TAG = "PoMo";
    static final String CLASS = MovieApplication.class.getSimpleName() + ": ";

    private static MovieApplication application;
    private RestClient client;
    private Bus bus = BusProvider.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, CLASS + " onCreate()");
        application = this;

        client = new RestClient(bus);
    }

    public MovieApplication getInstance(){
        return application;
    }

    public Bus getBus(){
        return this.bus;
    }
}
