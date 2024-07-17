package ru.practicum.ewm_main.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm_main.event.model.Events;

import java.time.LocalDateTime;
import java.util.List;

public interface EventsRepository extends JpaRepository<Events, Integer> {
    List<Events> findByInitiatorId(int userId, Pageable pageable);

    Events findByInitiatorIdAndAnnotation(int userId, String annotation);

    @Query("select e from Events e " +
           "where lower(e.annotation) like '%?1%' " +
           "or lower(e.description) like '%?1%' " +
           "and e.paid = ?2 " +
           "and e.eventDate between ?3 and ?4 " +
           "and e.confirmedRequests < e.participantLimit " +
           "order by e.views desc")
    public List<Events> getEventsAvailableOrderByViews(String text,
                                                       boolean paid,
                                                       LocalDateTime rangeStart,
                                                       LocalDateTime rangeEnd,
                                                       Pageable pageable);

    @Query("select e from Events e " +
            "where lower(e.annotation) like '%?1%' " +
            "or lower(e.description) like '%?1%' " +
            "and e.paid = ?2 " +
            "and e.eventDate between ?3 and ?4 " +
            "order by e.views desc")
    public List<Events> getEventsAllOrderByViews(String text,
                                                 boolean paid,
                                                 LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd,
                                                 Pageable pageable);

    @Query("select e from Events e " +
            "where lower(e.annotation) like '%?1%' " +
            "or lower(e.description) like '%?1%' " +
            "and e.paid = ?2 " +
            "and e.eventDate between ?3 and ?4 " +
            "and e.confirmedRequests < e.participantLimit " +
            "order by e.eventDate desc")
    public List<Events> getEventsAvailableOrderByEventDate(String text,
                                                           boolean paid,
                                                           LocalDateTime rangeStart,
                                                           LocalDateTime rangeEnd,
                                                           Pageable pageable);

    @Query("select e from Events e " +
            "where lower(e.annotation) like '%?1%' " +
            "or lower(e.description) like '%?1%' " +
            "and e.paid = ?2 " +
            "and e.eventDate between ?3 and ?4 " +
            "order by e.eventDate desc")
    public List<Events> getEventsAllOrderByEventDate(String text,
                                                     boolean paid,
                                                     LocalDateTime rangeStart,
                                                     LocalDateTime rangeEnd,
                                                     Pageable pageable);
}
