package com.example.matej.popularmoviesdemo;

/**
 * Created by matej on 5.2.2017.
 * Movie Model
 */

public class Movie {

    private String poster_path = null;
    private String title;
    private String id;

     Movie(String poster_path, String title, String id) {
        this.poster_path = poster_path;
        this.title = title;
        this.id = id;
    }

     String getPoster_path() {
        return ApiRequest.IMAGE_URL + ApiRequest.IMAGE_SIZE + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
