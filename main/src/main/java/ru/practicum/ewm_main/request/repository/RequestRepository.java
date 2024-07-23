package ru.practicum.ewm_main.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm_main.request.model.Request;
import ru.practicum.ewm_main.utility.Status;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findByEvent(int eventId);
    @Query("select r from Request r " +
           "where r.id in ?1 ")
    List<Request> findByIds(List<Integer> ids);

    List<Request> findByEventAndStatus(int eventId, Status status);

    List<Request> findByRequester(int userId);

    Optional<Request> findByRequesterAndEvent(int userId, int eventId);
}
