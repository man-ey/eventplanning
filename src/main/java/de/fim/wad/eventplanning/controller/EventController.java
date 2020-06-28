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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void config() throws IOException, ParseException {
        readEventTypes();
        initDB();
    }

    private void readEventTypes() throws IOException {
        FileReader fileReader = new FileReader("EventTypes.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = "";

        while ((line = bufferedReader.readLine()) != null) {
            saveEventType(new EventTypeDTO(line));
        }
        bufferedReader.close();
    }

    private boolean isInit() throws IOException {
        FileReader fileReader = new FileReader("InitDB.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        boolean isInit = Boolean.parseBoolean(bufferedReader.readLine());

        bufferedReader.close();

        return isInit;

    }

    private EventDTO convertToEventDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }

    private EventTypeDTO convertToEventTypeDTO(EventType eventType) {
        return modelMapper.map(eventType, EventTypeDTO.class);
    }

    private Event convertToEvent(EventDTO eventDTO) {
        return modelMapper.map(eventDTO, Event.class);
    }

    private EventType convertToEventType(EventTypeDTO eventTypeDTO) {
        return modelMapper.map(eventTypeDTO, EventType.class);
    }

    private Event convertToEvent(EventCreationDTO eventCreationDTO) {
        Event event = modelMapper.map(eventCreationDTO, Event.class);
        event.setLikes(0);
        event.setDislikes(0);
        return event;
    }




    @GetMapping("/api/events")
    public List<EventDTO> getAllEvents() {
        List<EventDTO> result = new ArrayList<>();
        for (Event event : eventService.getAll()) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }

    @GetMapping("/api/eventtypes")
    public List<EventTypeDTO> getAllEventsTypes() {
        List<EventTypeDTO> result = new ArrayList<>();
        for (EventType eventType : eventTypeService.getAll()) {
            result.add(convertToEventTypeDTO(eventType));
        }
        return result;
    }

    @GetMapping("/api/newest20")
    public List<EventDTO> newestTwenty() {
        List<EventDTO> result = new ArrayList<>();
        for (Event event : eventService.newestN(20)) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }

    @GetMapping("/init")
    public void initDB() throws ParseException, IOException {
        if (isInit()) {
            // initDB
            EventTypeDTO typ1 = new EventTypeDTO("A");
            EventTypeDTO typ2 = new EventTypeDTO("B");
            EventTypeDTO typ3 = new EventTypeDTO("C");
            saveEventType(typ1);
            saveEventType(typ2);
            saveEventType(typ3);

            EventCreationDTO ev1 = new EventCreationDTO("Event1", "First Event", "31.12.2020", "Passau", convertToEventType(typ2));
            EventCreationDTO ev5 = new EventCreationDTO("Event5", "Fifth Event", "31.12.2020", "München", convertToEventType(typ2));
            EventCreationDTO ev2 = new EventCreationDTO("Event2", "Sec Event", "31.12.2020", "Berlin", convertToEventType(typ2));
            EventCreationDTO ev3 = new EventCreationDTO("Event3", "Third Event", "31.12.2020", "Frankfurt", convertToEventType(typ2));
            EventCreationDTO ev4 = new EventCreationDTO("Event4", "Fourth Event", "31.12.2020", "Paris", convertToEventType(typ2));

            saveEvent(ev1);
            saveEvent(ev5);
            saveEvent(ev2);
            saveEvent(ev3);
            saveEvent(ev4);
        }
    }

    public void saveEventType(EventTypeDTO eventType) {
        eventTypeService.save(convertToEventType(eventType));
    }

    public void saveEvent(EventCreationDTO event) {
        eventService.save(convertToEvent(event));
    }

    @RequestMapping("/events")  // Bonus REST
    public List<EventDTO> getLastN(@RequestParam("n") int n) {
        List<EventDTO> result = new ArrayList<>();
        for (Event event : eventService.newestN(n)) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }
}