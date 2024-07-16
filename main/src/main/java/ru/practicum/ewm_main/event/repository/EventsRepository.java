package ru.practicum.ewm_main.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.event.model.Events;

public interface EventsRepository extends JpaRepository<Events, Integer> {
}
