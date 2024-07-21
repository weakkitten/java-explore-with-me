package ru.practicum.ewm_main.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm_main.event.model.Events;
import ru.practicum.ewm_main.utility.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventsRepository extends JpaRepository<Events, Integer> {
    List<Events> findByInitiatorId(int userId, Pageable pageable);

    Events findByInitiatorIdAndAnnotation(int userId, String annotation);
    Events findByIdAndState(int id, State state);

    @Query("select e from Events e " +
           "where lower(e.annotation) like '%?1%' " +
           "or lower(e.description) like '%?1%' " +
            "and e.categoryId in ?2" +
           "and e.paid = ?3 " +
           "and e.eventDate between ?4 and ?5 " +
           "and e.confirmedRequests < e.participantLimit " +
           "order by e.views desc")
    List<Events> getEventsAvailableOrderByViews(String text,
                                                List<Integer> categories,
                                                boolean paid,
                                                LocalDateTime rangeStart,
                                                LocalDateTime rangeEnd,
                                                Pageable pageable);

    @Query("select e from Events e " +
            "where lower(e.annotation) like '%?1%' " +
            "or lower(e.description) like '%?1%' " +
            "and e.categoryId in ?2" +
            "and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 " +
            "order by e.views desc")
    List<Events> getEventsAllOrderByViews(String text,
                                          List<Integer> categories,
                                          boolean paid,
                                          LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd,
                                          Pageable pageable);

    @Query("select e from Events e " +
            "where lower(e.annotation) like '%?1%' " +
            "or lower(e.description) like '%?1%' " +
            "and e.categoryId in ?2" +
            "and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 " +
            "and e.confirmedRequests < e.participantLimit " +
            "order by e.eventDate desc")
    List<Events> getEventsAvailableOrderByEventDate(String text,
                                                    List<Integer> categories,
                                                    boolean paid,
                                                    LocalDateTime rangeStart,
                                                    LocalDateTime rangeEnd,
                                                    Pageable pageable);

    @Query("select e from Events e " +
            "where lower(e.annotation) like '%?1%' " +
            "or lower(e.description) like '%?1%' " +
            "and e.categoryId in ?2" +
            "and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 " +
            "order by e.eventDate desc")
    List<Events> getEventsAllOrderByEventDate(String text,
                                              List<Integer> categories,
                                              boolean paid,
                                              LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd,
                                              Pageable pageable);

    @Query("select e from Events e " +
            "where lower(e.annotation) like '%?1%' " +
            "or lower(e.description) like '%?1%' " +
            "and e.categoryId in ?2" +
            "and e.paid = ?3 " +
            "and e.eventDate > ?4 " +
            "and e.confirmedRequests < e.participantLimit " +
            "order by e.views desc")
    List<Events> getEventsAvailableOrderByViewsWithoutTimes(String text,
                                                            List<Integer> categories,
                                                            boolean paid,
                                                            LocalDateTime rangeStart,
                                                            Pageable pageable);

    @Query("select e from Events e " +
            "where lower(e.annotation) like '%?1%' " +
            "or lower(e.description) like '%?1%' " +
            "and e.categoryId in ?2" +
            "and e.paid = ?3 " +
            "and e.eventDate > ?4 " +
            "order by e.views desc")
    List<Events> getEventsAllOrderByViewsWithoutTimes(String text,
                                                      List<Integer> categories,
                                                      boolean paid,
                                                      LocalDateTime rangeStart,
                                                      Pageable pageable);

    @Query("select e from Events e " +
            "where lower(e.annotation) like '%?1%' " +
            "or lower(e.description) like '%?1%' " +
            "and e.categoryId in ?2" +
            "and e.paid = ?3 " +
            "and e.eventDate > ?4 " +
            "and e.confirmedRequests < e.participantLimit " +
            "order by e.eventDate desc")
    List<Events> getEventsAvailableOrderByEventDateWithoutTimes(String text,
                                                                List<Integer> categories,
                                                                boolean paid,
                                                                LocalDateTime rangeStart,
                                                                Pageable pageable);

    @Query("select e from Events e " +
            "where lower(e.annotation) like '%?1%' " +
            "or lower(e.description) like '%?1%' " +
            "and e.categoryId in ?2" +
            "and e.paid = ?3 " +
            "and e.eventDate > ?4 " +
            "order by e.eventDate desc")
    List<Events> getEventsAllOrderByEventDateWithoutTimes(String text,
                                                          List<Integer> categories,
                                                          boolean paid,
                                                          LocalDateTime rangeStart,
                                                          Pageable pageable);
    //Для админ/гетИвентс
    @Query("select e from Events e " +
            "where e.initiatorId in ?1 " +
            "and e.state in ?2 " +
            "and e.categoryId in ?3 " +
            "and e.eventDate between ?4 and ?5")
    List<Events> getEventsWithUsersAndStatesAndCategoriesAndTimes(List<Integer> users,
                                    List<State> states,
                                    List<Integer> categories,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    Pageable pageable);

    @Query("select e from Events e " +
            "where e.eventDate between ?1 and ?2")
    List<Events> getEventsWithTimes(LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    Pageable pageable);

    @Query("select e from Events e " +
            "where e.categoryId in ?1 " +
            "and e.eventDate between ?2 and ?3")
    List<Events> getEventsWithCategoryAndTimes(List<Integer> categories,
                                               LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd,
                                               Pageable pageable);

    @Query("select e from Events e " +
            "where e.state in ?1 " +
            "and e.categoryId in ?2 " +
            "and e.eventDate between ?3 and ?4")
    List<Events> getEventsWithStateAndCategoriesAndTimes(List<State> states,
                                                         List<Integer> categories,
                                                         LocalDateTime rangeStart,
                                                         LocalDateTime rangeEnd,
                                                         Pageable pageable);

    @Query("select e from Events e " +
            "where e.state in ?1 " +
            "and e.eventDate between ?2 and ?3")
    List<Events> getEventsWithStateAndTimes(List<State> states,
                                                         LocalDateTime rangeStart,
                                                         LocalDateTime rangeEnd,
                                                         Pageable pageable);

    @Query("select e from Events e " +
            "where e.initiatorId in ?1 " +
            "and e.eventDate between ?2 and ?3")
    List<Events> getEventsWithUsersAndTimes(List<Integer> users,
                                            LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd,
                                            Pageable pageable);

    @Query("select e from Events e " +
            "where e.initiatorId in ?1 " +
            "and e.categoryId in ?2 " +
            "and e.eventDate between ?3 and ?4")
    List<Events> getEventsWithUsersAndTimesAndCategories(List<Integer> users,
                                                         List<Integer> categories,
                                                         LocalDateTime rangeStart,
                                                         LocalDateTime rangeEnd,
                                                         Pageable pageable);

    @Query("select e from Events e " +
            "where e.initiatorId in ?1 " +
            "and e.state in ?2 " +
            "and e.eventDate between ?3 and ?4")
    List<Events> getEventsWithUsersAndStatesAndTimes(List<Integer> users,
                                                     List<State> states,
                                                     LocalDateTime rangeStart,
                                                     LocalDateTime rangeEnd,
                                                     Pageable pageable);
    //Без времени
    @Query("select e from Events e " +
            "where e.eventDate > ?1")
    List<Events> getEventsWithoutTimes(LocalDateTime rangeEnd,
                                       Pageable pageable);

    @Query("select e from Events e " +
            "where e.categoryId in ?1 " +
            "and e.eventDate > ?2")
    List<Events> getEventsWithCategory(List<Integer> categories,
                                       LocalDateTime rangeEnd,
                                       Pageable pageable);

    @Query("select e from Events e " +
            "where e.state in ?1 " +
            "and e.categoryId in ?2 " +
            "and e.eventDate > ?3")
    List<Events> getEventsWithStateAndCategories(List<State> states,
                                                 List<Integer> categories,
                                                 LocalDateTime rangeEnd,
                                                 Pageable pageable);

    @Query("select e from Events e " +
            "where e.state in ?1 " +
            "and e.eventDate > ?2")
    List<Events> getEventsWithState(List<State> states,
                                    LocalDateTime rangeEnd,
                                    Pageable pageable);

    @Query("select e from Events e " +
            "where e.initiatorId in ?1 " +
            "and e.eventDate > ?2")
    List<Events> getEventsWithUsers(List<Integer> users,
                                    LocalDateTime rangeEnd,
                                    Pageable pageable);

    @Query("select e from Events e " +
            "where e.initiatorId in ?1 " +
            "and e.categoryId in ?2 " +
            "and e.eventDate > ?3")
    List<Events> getEventsWithUsersAndCategories(List<Integer> users,
                                                 List<Integer> categories,
                                                 LocalDateTime rangeEnd,
                                                 Pageable pageable);

    @Query("select e from Events e " +
            "where e.initiatorId in ?1 " +
            "and e.state in ?2 " +
            "and e.eventDate > ?3")
    List<Events> getEventsWithUsersAndState(List<Integer> users,
                                    List<State> states,
                                    LocalDateTime rangeEnd,
                                    Pageable pageable);

    @Query("select e from Events e " +
            "where e.initiatorId in ?1 " +
            "and e.state in ?2 " +
            "and e.categoryId in ?3 " +
            "and e.eventDate > ?4")
    List<Events> getEventsWithUsersAndStatesAndCategoriesWithoutTimes(List<Integer> users,
                                                                      List<State> states,
                                                                      List<Integer> categories,
                                                                      LocalDateTime rangeEnd,
                                                                      Pageable pageable);
}
