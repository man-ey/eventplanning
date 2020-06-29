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
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        String typesString = bufferedReader.readLine();

        bufferedReader.close();

        typesString = typesString.replace(" ", "");

        String[] types = typesString.split(",");

        for (String type : types) {
            saveEventType(new EventTypeDTO(type));
        }
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

    @GetMapping("/init")
    private void initDB() throws ParseException, IOException {
        if (isInit()) {
            EventTypeDTO typ1 = new EventTypeDTO("A");

            EventTypeDTO typ2 = new EventTypeDTO("B");

            EventTypeDTO typ3 = new EventTypeDTO("C");

            saveEventType(typ1);
            saveEventType(typ2);
            saveEventType(typ3);

            EventCreationDTO ev1 = new EventCreationDTO("Event1",
                    "First Event", "29.06.2020", "Passau",
                    convertToEventType(typ2));

            EventCreationDTO ev5 = new EventCreationDTO("Event5",
                    "Fifth Event", "31.12.2020", "München",
                    convertToEventType(typ1));

            EventCreationDTO ev2 = new EventCreationDTO("PastEvent",
                    "Sec Event", "31.12.2000",  convertToEventType(typ2),
                    13.389191, 52.50536);

            saveEvent(ev1);
            saveEvent(ev5);
            saveEvent(ev2);
        }
    }

    // Interface
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

    @RequestMapping("like")
    public void like(@RequestParam("name") String eventName) {
        Event event = eventService.find(eventName);

        if (event != null) {
            event.like();
            eventService.update(event);
        } else {
            throw new IllegalArgumentException("Event does not exist.");
        }
    }

    @RequestMapping("dislike")
    public void dislike(@RequestParam("name") String eventName) {
        Event event = eventService.find(eventName);

        if (event != null) {
            event.dislike();
            eventService.update(event);
        } else {
            throw new IllegalArgumentException("Event does not exist.");
        }
    }

    @GetMapping("/api/top")
    public List<EventDTO> topThree() {
        List<EventDTO> result = new ArrayList<>();

        for (Event event : eventService.top(3)) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }

    public void saveEventType(EventTypeDTO eventType) {
        if (!eventTypeService.existsByID(eventType.getEventType())) {
            eventTypeService.save(convertToEventType(eventType));
        } else {
            throw new IllegalArgumentException("Event already exists!");
        }
    }

    public void saveEvent(EventCreationDTO event) {
        if (!eventService.existsByID(event.getName())) {
            eventService.save(convertToEvent(event));
        } else {
            throw new IllegalArgumentException("Event already exists!");
        }
    }

    @RequestMapping("/filter")
    public List<EventDTO> testFilter() {
        EventType e1 = eventTypeService.find("A");

        EventType e2 = eventTypeService.find("B");

        return filterType(convertToEventTypeDTO(e2), 20);
    }

    public List<EventDTO> filterType(EventTypeDTO eventTypeDTO, int amount) {
        List<EventDTO> result = new ArrayList<>();

        for (Event event : eventService
                .filter(eventTypeDTO.getEventType(), amount)) {
            result.add(convertToEventDTO(event));
        }
        return result;
    }

    @RequestMapping("/search")
    public List<EventDTO> testSearch(@RequestParam("q") String query) {
        return search(query);
    }

    public List<EventDTO> search(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Empty query");
        }

        String[] queryWords = query.split(" ");

        Set<Event>[] seperateQueryResults = new HashSet[queryWords.length];

        for (int i = 0; i < queryWords.length; i++) {
            seperateQueryResults[i] = eventService.search(queryWords[i]);
        }

        Set<Event> intersection = new HashSet<>(seperateQueryResults[0]);

        for (int i = 0; i < queryWords.length; i++) {
            intersection.retainAll(seperateQueryResults[i]);
        }

        List<EventDTO> result = new ArrayList<>();

        for (Event event : intersection) {
            result.add(convertToEventDTO(event));
        }
        return result;
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
