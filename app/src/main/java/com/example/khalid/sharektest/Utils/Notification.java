package com.example.khalid.sharektest.Utils;

/**
 * Created by Abdelrahman on 5/5/2017.
 */

public class Notification {

    private String userId, date, body, posterId;

    public Notification(String userId, String date, String body, String posterId) {
        this.userId = userId;
        this.date = date;
        this.body = body;
        this.posterId = posterId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }
}
