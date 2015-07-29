package com.example.vit.popularmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.rest.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vit on 2015-07-23.
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    static final String CLASS = TrailersAdapter.class.getSimpleName() + ": ";

    List<Trailer> trailersList;
    private Context context;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;

        public ViewHolder(View v) {
            super(v);
            thumbnail = (ImageView) v.findViewById(R.id.ivTrailerThumbnail);
        }
    }

    public TrailersAdapter(Context context, List<Trailer> data) {
        this.context = context;
        this.trailersList = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trailers_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(buildYoutubeImageUrl(trailersList.get(position).getKey()))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return trailersList.size();
    }

    public void setData(List<Trailer> data){
        this.trailersList.clear();
        this.trailersList.addAll(data);
        notifyDataSetChanged();
    }

    static private String buildYoutubeImageUrl(String key){
        return "http://img.youtube.com/vi/" + key + "/0.jpg";
    }

}
