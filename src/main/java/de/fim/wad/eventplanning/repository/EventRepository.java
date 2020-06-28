package de.fim.wad.eventplanning.repository;

import de.fim.wad.eventplanning.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

   // public static final int oneDay = 24*60*60*1000;



    @Query(value = "select * from event where (date >= '2019-11-11') order by creation_time", nativeQuery = true)
    List<Event> newest();

}