package ru.practicum.ewm_main.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm_main.event.model.CompilationsEvents;

import java.util.List;

public interface CompilationsEventsRepository extends JpaRepository<CompilationsEvents, Integer> {
    List<CompilationsEvents> findByCompilationId(int compId);

    @Query("select c.eventId from CompilationsEvents c " +
           "where c.compilationId = ?1")
    List<Integer> getEventIds(int compId);
}
