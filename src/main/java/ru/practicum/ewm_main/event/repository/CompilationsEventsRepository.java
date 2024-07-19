package ru.practicum.ewm_main.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.event.model.CompilationsEvents;

import java.util.List;

public interface CompilationsEventsRepository extends JpaRepository<CompilationsEvents, Integer> {
    public List<CompilationsEvents> findByCompilationId(int compId);
}
