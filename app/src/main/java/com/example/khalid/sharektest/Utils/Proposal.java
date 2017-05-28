package com.example.khalid.sharektest.Utils;

/**
 * Created by Khalid on 4/8/2017.
 */
public class Proposal {
    private String title, description, price, duration, pieces, startDate, posterId, user;
    private boolean accepted;

    public Proposal(String title, String description, String price, String duration, String pieces, String startDate, String posterId, boolean accepted, String user) {
        this.accepted = accepted;
        this.pieces = pieces;
        this.title = title;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.startDate = startDate;
        this.posterId = posterId;
        this.user = user;
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

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
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

    public String getPieces() {
        return pieces;
    }

    public void setPieces(String pieces) {
        this.pieces = pieces;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {

        this.startDate = startDate;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
