package de.fim.wad.eventplanning.service;

import de.fim.wad.eventplanning.model.Event;
import de.fim.wad.eventplanning.model.EventType;
import de.fim.wad.eventplanning.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    public void save(Event event) {
        if (existsByID(event.getName())) {
            throw new IllegalArgumentException("Event already exists!");
        } else {
            eventRepository.save(event);
        }
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Event find(String name) {
        Event result = null;
        if (existsByID(name)) {
            result = eventRepository.findById(name).get();
        }
        return result;
    }

    public void update(Event event) {
        if (existsByID(event.getName())) {
            eventRepository.save(event);
        } else {
            throw new IllegalArgumentException("Event does not exists");
        }
    }

    public List<Event> newestN(int n) {
        return reduce(eventRepository.newest(), n);
    }

    public List<Event> top(int n) {
        return reduce(eventRepository.top(), n);
    }

    public boolean existsByID(String name) {
        return eventRepository.existsById(name);
    }

    public List<Event> filter(String eventType, int n) {
        return reduce(eventRepository.filter(eventType), n);
    }

    private List<Event> reduce(List<Event> events, int max) {
        List<Event> result = new ArrayList<>();

        if (events.size() > max) {
            for (int i = 0; i < max; i++) {
                result.add(events.get(i));
            }
        } else {
            result = events;
        }
        return result;
    }

    public Set<Event> search(String queryWord) {
        Set<Event> result = new HashSet<>();
        result.addAll(eventRepository.searchName(queryWord));
        result.addAll(eventRepository.searchLocation(queryWord));
        return result;
    }
}
