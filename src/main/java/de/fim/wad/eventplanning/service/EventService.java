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
        eventRepository.save(event);
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }


    public List<Event> newestTwenty() {
        List<Event> newest = eventRepository.newest();

        List<Event> result = new ArrayList<>();

        if (newest.size() > 20) {
            for (int i = 0; i < 20; i++) {
                result.add(newest.get(i));
            }
            return result;
        } else {
            return newest;
        }
    }
}
