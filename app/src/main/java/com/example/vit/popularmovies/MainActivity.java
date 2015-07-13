package com.example.vit.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vit.popularmovies.rest.RestClient;
import com.example.vit.popularmovies.rest.conf.Constants;
import com.example.vit.popularmovies.rest.model.Page;

import retrofit.Callback;
import retrofit.RetrofitError;


public class MainActivity extends ActionBarActivity {

    static final String TAG = "PoMo";
    static final String CLASS = MainActivity.class.getSimpleName() + ": ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
