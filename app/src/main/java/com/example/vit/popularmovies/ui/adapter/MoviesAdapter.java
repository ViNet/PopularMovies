package com.example.vit.popularmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.rest.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    static final String CLASS = MoviesAdapter.class.getSimpleName() + ": ";

    List<Movie> moviesList;
    private Context context;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView poster;

        public ViewHolder(View v) {
            super(v);
            poster = (ImageView) v.findViewById(R.id.ivItemPoster);
        }
    }

    public MoviesAdapter(Context context) {
        this.context = context;
        this.moviesList = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_grid_item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.textView.setText(dataset[position]);
        //Log.d(MovieApplication.TAG, CLASS + "url: " + buildUrl(moviesList.get(position).getPosterPath()));

        Picasso.with(context).load(buildUrl(moviesList.get(position).getPosterPath()))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void setData(List<Movie> data){
        this.moviesList.clear();
        addData(data);
    }

    public void addData(List<Movie> data){
        moviesList.addAll(data);
        notifyDataSetChanged();
    }

    public String buildUrl(String posterPath) {
        final String size = "w185";
        return "http://image.tmdb.org/t/p/" + size + "/" + posterPath;
    }

}
