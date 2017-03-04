package com.example.matej.popularmoviesdemo;

/**
 * Created by matej on 1.3.2017
 * Video Model
 */

class Video {

    private String id;
    private String name;
    private String type;
    private String key;

    Video(String id, String name, String type, String key) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.key = key;
    }

    String getId() { return id; }
    String getName() { return name; }
    String getType() { return type; }
    String getKey() { return key; }

    public void setId(String id) {
        this.id = id;
    }




}
