package com.example.khalid.sharektest.Utils;

/**
 * Created by Khalid on 11/15/2016.
 */
public class Poster {
    private String title, description, price, city, picUrl;

    public Poster(String title, String description, String price, String city, String picUrl) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.city = city;
        this.picUrl = picUrl;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
