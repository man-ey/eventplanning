package de.fim.wad.eventplanning.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="EVENTTYPE")
public class EventType {

    @Id
    private String eventType;

    public EventType() {

    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
