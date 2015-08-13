package com.example.vit.popularmovies;

import android.app.Application;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.vit.popularmovies.communication.BusProvider;
import com.example.vit.popularmovies.communication.EventMessenger;
import com.example.vit.popularmovies.rest.RestClient;
import com.squareup.otto.Bus;


public class MovieApplication extends Application {

    public static final String TAG = "PoMo";
    static final String CLASS = MovieApplication.class.getSimpleName() + ": ";

    private RestClient client = RestClient.getInstance();
    private DataController dataController = DataController.getInstance();;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, CLASS + " onCreate()");
        SharedPreferencesManager.init(getBaseContext());
        EventMessenger.init();
    }

}
