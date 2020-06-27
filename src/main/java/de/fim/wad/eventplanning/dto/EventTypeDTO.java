package de.fim.wad.eventplanning.dto;

public class EventTypeDTO {
    private String eventType;

    public EventTypeDTO(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}