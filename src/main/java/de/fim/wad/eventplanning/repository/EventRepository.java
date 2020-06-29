package de.fim.wad.eventplanning.repository;

import de.fim.wad.eventplanning.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    /** Returns the future events in oder of their creation time.
     * @return List of events
     */
    @Query(value = "select * from event where (event.date >= CURRENT_DATE) "
            + "order by event.creation_time", nativeQuery = true)
    List<Event> newest();

    /** Returns the events in oder of their likes.
     * @return List of events.
     */
    @Query(value = "select * from event order by event.likes desc",
            nativeQuery = true)
    List<Event> top();

    /** Returns future events of a given event typ.
     * @param   eventType Desired event typ.
     * @return  List of events.
     */
    @Query(value = "select * from event "
            + "where (event.event_type_event_type = ?1)"
            + " and (event.date >= CURRENT_DATE)"
            + "order by event.creation_time", nativeQuery = true)
    List<Event> filter(String eventType);

    @Query(value = "select * from event where event.NAME LIKE %?1%",
            nativeQuery = true)
    Set<Event> searchName(String queryWord);

    @Query(value = "select * from event where event.LOCATION LIKE %?1%",
            nativeQuery = true)
    Set<Event> searchLocation(String queryWord);
}
