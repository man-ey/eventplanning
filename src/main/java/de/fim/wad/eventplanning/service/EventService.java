package de.fim.wad.eventplanning.service;

import de.fim.wad.eventplanning.model.Event;
import de.fim.wad.eventplanning.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

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
        List<Event> newest = eventRepository.newest();

        List<Event> result = new ArrayList<>();

        if (newest.size() > n) {
            for (int i = 0; i < n; i++) {
                result.add(newest.get(i));
            }
        } else {
            result = newest;
        }
        return result;
    }

    public List<Event> top(int n) {
        List<Event> top = eventRepository.top();

        List<Event> result = new ArrayList<>();

        if (top.size() > n) {
            for (int i = 0; i < n; i++) {
                result.add(top.get(i));
            }
        } else {
            result = top;
        }
        return result;
    }

    public boolean existsByID(String name) {
        return eventRepository.existsById(name);
    }
}
