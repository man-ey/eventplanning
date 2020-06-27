package de.fim.wad.eventplanning.controller;

import de.fim.wad.eventplanning.dto.EventCreationDTO;
import de.fim.wad.eventplanning.dto.EventDTO;
import de.fim.wad.eventplanning.dto.EventTypeDTO;
import de.fim.wad.eventplanning.model.Event;
import de.fim.wad.eventplanning.model.EventType;
import de.fim.wad.eventplanning.service.EventService;
import de.fim.wad.eventplanning.service.EventTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private ModelMapper modelMapper;

    public EventDTO convertToEventDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }

    public EventTypeDTO convertToEventTypeDTO(EventType eventType) {
        return modelMapper.map(eventType, EventTypeDTO.class);
    }

    public Event convertToEvent(EventDTO eventDTO) {
        return modelMapper.map(eventDTO, Event.class);
    }

    public EventType convertToEventType(EventTypeDTO eventTypeDTO) {
        return modelMapper.map(eventTypeDTO, EventType.class);
    }

    public Event convertToEvent(EventCreationDTO eventCreationDTO) {
        Event event = modelMapper.map(eventCreationDTO, Event.class);
        event.setLikes(0);
        event.setDislikes(0);
        return event;
    }




    @GetMapping("/api/events")
    public List<Event> getAllEvents() {
        return eventService.getAll();
    }

    @GetMapping("/api/eventtypes")
    public List<EventType> getAllEventsTypes() {
        return eventTypeService.getAll();
    }

    @GetMapping("/api/newest")
    public List<Event> newestTwenty() {
        return eventService.newestTwenty();
    }

    @GetMapping("/init")
    public void initDB() throws ParseException {
        // initDB
        EventType eventType1 = new EventType();
        EventType eventType2 = new EventType();
        EventType eventType3 = new EventType();
        eventType1.setEventType("A");
        eventType2.setEventType("B");
        eventType3.setEventType("C");
        eventTypeService.save(eventType1);
        eventTypeService.save(eventType2);
        eventTypeService.save(eventType3);

        Event event1 = new Event();
        event1.setName("Event 1");
        event1.setDescription("Best event");
        event1.setLocation("Passau");
        event1.setEventType(eventType2);
        event1.setDate(new SimpleDateFormat("dd.mm.yyyy").parse("30.06.2020"));
        event1.setLongitude(1);
        event1.setLatitude(1);
        event1.setLikes(1);
        event1.setDislikes(3);
        eventService.save(event1);

        Event event3 = new Event();
        event3.setName("Event 3");
        event3.setDescription("Second event");
        event3.setLocation("Berlin");
        event3.setEventType(eventType1);
        event3.setDate(new SimpleDateFormat("dd.mm.yyyy").parse("30.06.2022"));
        event3.setLongitude(13);
        event3.setLatitude(13);
        event3.setLikes(3);
        event3.setDislikes(1);
        eventService.save(event3);

        Event event2 = new Event();
        event2.setName("Event 2");
        event2.setDescription("Second event");
        event2.setLocation("München");
        event2.setEventType(eventType2);
        event2.setDate(new SimpleDateFormat("dd.mm.yyyy").parse("30.06.2021"));
        event2.setLongitude(22.2);
        event2.setLatitude(22.2);
        event2.setLikes(2);
        event2.setDislikes(2);
        eventService.save(event2);
    }

    public void saveEventType(EventType eventType) {
        eventTypeService.save(eventType);
    }

    public void saveEvent(Event event) {
        eventService.save(event);
    }
}