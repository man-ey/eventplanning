package de.fim.wad.eventplanning.dto;

import de.fim.wad.eventplanning.APIs.GeoAPI;
import de.fim.wad.eventplanning.model.EventType;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EventCreationDTO {
    private String name;

    private String description;

    private Timestamp date;

    private String location;

    private EventType eventType;

    private double longitude;

    private double latitude;

    public EventCreationDTO() {

    }

    public EventCreationDTO(String name, String description, String date, String location,
                            EventType eventType) throws ParseException {
        this.name = name;
        this.description = description;
        this.date = new Timestamp(new SimpleDateFormat("dd.MM.yyyy").parse(date).getTime());
        this.location = location;
        this.eventType = eventType;
        double[] coords = GeoAPI.locationToLatLng(location);
        this.longitude = coords[0];
        this.latitude = coords[1];
    }

    public EventCreationDTO(String name, String description, String date, EventType eventType, Double longitude,
                            Double latitude) throws ParseException {
        this.name = name;
        this.description = description;
        this.date = new Timestamp(new SimpleDateFormat("dd.MM.yyyy").parse(date).getTime());
        this.location = GeoAPI.latLngtoCity(latitude, longitude);
        this.eventType = eventType;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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
}