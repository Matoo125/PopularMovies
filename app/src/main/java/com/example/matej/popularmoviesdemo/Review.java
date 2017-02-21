package com.example.matej.popularmoviesdemo;

/**
 * Created by matej on 5.2.2017.
 * Movie Model
 */

class Review {

    private String id;
    private String author;
    private String content;
    private String url;

    Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    String getId() { return id; }
    String getAuthor() { return author; }
    String getContent() { return content; }
    String getUrl() { return url; }

    public void setId(String id) {
        this.id = id;
    }




}
