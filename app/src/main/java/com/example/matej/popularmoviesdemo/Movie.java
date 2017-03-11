package com.example.matej.popularmoviesdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by matej on 5.2.2017.
 * Movie Model
 */

class Movie implements Parcelable {

    private String poster_path = null;
    private String title;
    private String id;
    private String release_date;
    private String users_rating;
    private String description;

    Movie(String poster_path, String title, String id, String release_date, String users_rating, String description) {
        this.poster_path = poster_path;
        this.title = title;
        this.id = id;
        this.release_date = release_date;
        this.users_rating = users_rating;
        this.description = description;
    }

    String getPoster_path() {
        return poster_path;
    }


    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String getDescription() {
        return description;
    }

    String getUsers_rating() {
        return users_rating;
    }

    String getRelease_date() {
        return release_date;
    }


    protected Movie(Parcel in) {
        poster_path = in.readString();
        title = in.readString();
        id = in.readString();
        release_date = in.readString();
        users_rating = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(title);
        dest.writeString(id);
        dest.writeString(release_date);
        dest.writeString(users_rating);
        dest.writeString(description);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}