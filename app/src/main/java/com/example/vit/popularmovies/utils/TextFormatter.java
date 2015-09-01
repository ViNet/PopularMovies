package com.example.vit.popularmovies.utils;


import java.text.NumberFormat;
import java.util.Locale;

public class TextFormatter {

    public static String getCurrency(double value){
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormatter.setMaximumFractionDigits(0);
        return currencyFormatter.format(value);
    }

}
