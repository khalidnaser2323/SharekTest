package com.example.khalid.sharektest.Utils;

/**
 * Created by Khalid on 11/15/2016.
 */
public class Poster {
    private String title, description, price, duration, picUrl, posterID;

    public Poster(String posterID, String title, String description, String price, String duration, String picUrl) {
        this.posterID = posterID;
        this.title = title;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.picUrl = picUrl;
    }

    public String getPosterID() {
        return posterID;
    }

    public void setPosterID(String posterID) {
        this.posterID = posterID;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
