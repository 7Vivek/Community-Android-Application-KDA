package com.project.kda.Model;

public class AdminImageRegistration {
    public String imageId;
    public String imageTitle;
    public String eventimage;
    public String eventDate;

    public AdminImageRegistration(){
    }

    public AdminImageRegistration(String imageId,String imageTitle,String eventimage,String eventDate) {
        this.imageId = imageId;
        this.imageTitle = imageTitle;
        this.eventimage = eventimage;
        this.eventDate=eventDate;
    }

    public String getImageId() {
        return imageId;
    }

    public void getImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getEventimage() {
        return eventimage;
    }

    public void setEventimage(String eventimage) {
        this.eventimage = eventimage;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
