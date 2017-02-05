package com.example.matej.popularmoviesdemo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matej on 5.2.2017.
 * custom grid adapter
 */

 class CustomGridAdapter extends ArrayAdapter<Movie> {

    private ArrayList<Movie> movies;
    private Context context;
    private int resource;

    CustomGridAdapter(Context context, int resource, ArrayList<Movie> movies) {
        super(context, resource, movies);

        this.movies = movies;
        this.context = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grid_item, null, true);
        }

        Movie movie = getItem(position);
        String poster = movie.getPoster_path();

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewPoster);
        Picasso.with(context).load(poster).placeholder(R.drawable.placeholder).into(imageView);



        return convertView;
    }
}
