package de.fim.wad.eventplanning.repository;

import de.fim.wad.eventplanning.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    @Query(value = "select * from EVENT order by creation_Time", nativeQuery = true)
    List<Event> newest();
}