package ru.practicum.ewm_main.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.event.model.Views;

import java.util.Optional;

public interface ViewsRepository extends JpaRepository<Views, Integer> {
    Optional<Views> findByEventIdAndIp(int id, String ip);
}
