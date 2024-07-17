package ru.practicum.ewm_main.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    public List<Request> findByEvent(int eventId);
    public List<Request> findByRequester(int userId);
}
