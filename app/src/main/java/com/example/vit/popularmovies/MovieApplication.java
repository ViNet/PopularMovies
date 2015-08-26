package com.example.vit.popularmovies;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.vit.popularmovies.communication.EventMessenger;
import com.example.vit.popularmovies.rest.RestClient;
import com.example.vit.popularmovies.utils.SharedPreferencesManager;


public class MovieApplication extends Application {

    public static final String TAG = "PoMo";
    static final String CLASS = MovieApplication.class.getSimpleName() + ": ";

    private static MovieApplication instance;

    private RestClient client = RestClient.getInstance();
    private DataController dataController = DataController.getInstance();

    public static MovieApplication getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        Log.d(TAG, CLASS + " onCreate()");
        SharedPreferencesManager.init(getBaseContext());
        EventMessenger.init();
    }
}
