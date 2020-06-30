package de.fim.wad.eventplanning.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "EVENT")
public class Event {
    @Id
    private String name;
    private Date date;
    private String location;
    @ManyToOne
    private EventType eventType;
    private double longitude;
    private double latitude;
    private String description;
    private int likes;
    private int dislikes;
    private Timestamp creationTime;

    public Event() {
        likes = 0;
        dislikes = 0;
        creationTime = new Timestamp(System.currentTimeMillis());

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

    public void setLocation(double latitude, double longitude) {
        if (latitude >= -180 && longitude >= -180
                && latitude <= 180 && longitude <= 180) {
            this.location = location;
        } else {
            throw new IllegalArgumentException("Illegal coordinates");
        }
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

    public void like() {
        likes++;
    }

    public void dislike() {
        dislikes++;
    }

    public void removeDislike() {
        dislikes--;
    }

    public void removeLike() {
        likes--;
    }


    public Timestamp getCreationTime() {
        return creationTime;
    }
}
