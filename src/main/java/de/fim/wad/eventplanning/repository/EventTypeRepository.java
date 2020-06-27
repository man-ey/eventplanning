package de.fim.wad.eventplanning.repository;

import de.fim.wad.eventplanning.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, String> {
}
