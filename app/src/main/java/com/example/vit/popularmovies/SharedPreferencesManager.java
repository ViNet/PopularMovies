package com.example.vit.popularmovies;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesManager {

    static final String CLASS = SharedPreferencesManager.class.getSimpleName() + ": ";

    private static SharedPreferencesManager manager;
    private static Context context;

    public static void init(Context context){
        if(manager == null){
            manager = new SharedPreferencesManager(context);
        }
    }

    private SharedPreferencesManager(Context baseContext){
        context = baseContext;
    }

    public static String getPrefSortBy(){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(context.getString(R.string.pref_order_key)
                , context.getString(R.string.param_sort_by_popularity_desc));

    }
}
