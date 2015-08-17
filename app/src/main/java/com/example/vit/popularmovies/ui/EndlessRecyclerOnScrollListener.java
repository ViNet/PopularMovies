package com.example.vit.popularmovies.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.vit.popularmovies.MovieApplication;


public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener{

    public static String CLASS = EndlessRecyclerOnScrollListener.class.getSimpleName() + ": ";

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private GridLayoutManager layoutManager;

    public EndlessRecyclerOnScrollListener(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int totalItemCount = layoutManager.getItemCount();
        int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();


        // if item can't be completely visible
        if(lastVisible == RecyclerView.NO_POSITION){
            lastVisible = layoutManager.findLastVisibleItemPosition();
        }
        if(loading){
            // when success loaded
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading) {
            // end of list reached
            if ( lastVisible >= (totalItemCount - 1)) {
                loading = true;
                // load next page
                onLoadMore();
            }
        }
    }


    public abstract void onLoadMore();
}
