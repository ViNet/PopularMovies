package com.example.vit.popularmovies.utils;


import android.util.Log;

import com.example.vit.popularmovies.MovieApplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    final static String CLASS = DateUtils.class.getSimpleName() + ": ";

    // input 2001-12-19
    public static String formatDate(String date){
        final String oldFormat = "yyyy-mm-dd";
        final String newFormat = "d MMMM yyyy";
        DateFormat dt = new SimpleDateFormat(oldFormat, Locale.US);
        String formattedDate = "";
        try {
            Date newDate = dt.parse(date);
            DateFormat dt2 = new SimpleDateFormat(newFormat, Locale.US);
            formattedDate = dt2.format(newDate);

        } catch (ParseException e){
            Log.d(MovieApplication.TAG, CLASS + e.getMessage());
        }

        return formattedDate;
    }
}
