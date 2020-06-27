package de.fim.wad.eventplanning.dto;

import de.fim.wad.eventplanning.model.EventType;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class EventDTO {
    private String name;

    private Date date;

    private String location;

    private EventType eventType;

    private double longitude;

    private double latitude;

    private String description;

    private int likes;

    private int dislikes;

    private Timestamp creationTime;

    public EventDTO() {

    }

    public EventDTO(String name, String description, Date date, String location, EventType eventType, Double longitude,
                    Double latitude, int likes, int dislikes, Timestamp creationTime) throws ParseException {
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
        this.eventType = eventType;
        this.longitude = longitude;
        this.latitude = latitude;
        this.likes = likes;
        this.dislikes = dislikes;
        this.creationTime = creationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
}