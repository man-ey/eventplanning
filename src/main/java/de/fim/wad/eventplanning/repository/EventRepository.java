package de.fim.wad.eventplanning.repository;

import de.fim.wad.eventplanning.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

   // public static final int oneDay = 24*60*60*1000;



    @Query(value = "select * from event where (event.date >= CURRENT_TIMESTAMP) order by event.creation_time", nativeQuery = true) //TODO: BUGFIX
    List<Event> newest();

    @Query(value = "select * from event order by event.likes desc", nativeQuery = true)
    List<Event> top();

    @Query(value = "select * from event where event. event_type_event_type = ?1", nativeQuery = true) // TODO: add future only
    List<Event> filter(String eventType);

    @Query(value = "select * from event where event.NAME LIKE %?1%", nativeQuery = true)
    Set<Event> searchName(String queryWord);

    @Query(value = "select * from event where event.LOCATION LIKE %?1%", nativeQuery = true)
    Set<Event> searchLocation(String queryWord);
}