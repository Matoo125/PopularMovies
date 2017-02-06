package com.example.matej.popularmoviesdemo;

/**
 * Created by matej on 5.2.2017.
 * Movie Model
 */

public class Movie {

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
        return ApiRequest.IMAGE_URL + ApiRequest.IMAGE_SIZE + poster_path;
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

    public String getDescription() {
        return description;
    }

    public String getUsers_rating() {
        return users_rating;
    }

    public String getRelease_date() {
        return release_date;
    }

}
