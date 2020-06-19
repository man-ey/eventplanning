package de.fim.wad.eventplanning.models;

public class Event {
    private String name;
    private String date;
    private String location;
    private String description;
    private String type;
    private int likes;
    private int dislikes;

    public Event(String name, String date, String location, String description, String type, int likes, int dislikes) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.type = type;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }
}
