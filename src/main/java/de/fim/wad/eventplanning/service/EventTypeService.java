package de.fim.wad.eventplanning.service;

import de.fim.wad.eventplanning.model.EventType;
import de.fim.wad.eventplanning.repository.EventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;


    public void save(EventType eventType) {
        eventTypeRepository.save(eventType);
    }

    public List<EventType> getAll() {
        return eventTypeRepository.findAll();
    }

    public boolean existsByID(String name) {
        return eventTypeRepository.existsById(name);
    }
}
