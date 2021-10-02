package com.project.kda.Model;

public class AdminEventRegistration {
    public String eventId;
    public String eventTitle;
    public String eventDescription;
    public String eventimage;
    public String eventDate;

    public AdminEventRegistration() {
    }

    public AdminEventRegistration(String eventId, String eventTitle, String eventDescription, String eventimage,String eventDate) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventimage = eventimage;
        this.eventDate=eventDate;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
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
