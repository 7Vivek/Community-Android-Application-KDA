package com.project.kda.Model;

public class Event {

    public String eventId;
    public String title;
    public String date;
    public String description;
    public String like;
    public String share;
    public String coverPhoto;


    public Event(String eventId, String title, String date, String description, String like, String share, String coverPhoto) {
        this.eventId = eventId;
        this.title = title;
        this.date = date;
        this.description = description;
        this.like = like;
        this.share = share;
        this.coverPhoto = coverPhoto;
    }

    public String eventId() {
        return eventId;
    }

    public void eventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

}






