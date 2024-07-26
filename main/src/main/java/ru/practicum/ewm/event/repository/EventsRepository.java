package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.model.Events;
import ru.practicum.ewm.utility.State;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventsRepository extends JpaRepository<Events, Integer> {
    List<Events> findByInitiatorId(int userId, Pageable pageable);

    Events findByInitiatorIdAndAnnotation(int userId, String annotation);

    @Query("select e.categoryId from Events e " +
        "where e.categoryId = ?1")
    Optional<List<Integer>> findByCategoryId(int categoryId);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?1 " +
            "order by e.views desc")
    List<Events> getEventsPaidAndAvailable(LocalDateTime now,
                                           Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.eventDate > ?1 " +
            "order by e.views desc")
    List<Events> getEventsPaid(LocalDateTime now,
                               Pageable pageable);

    @Query("select e from Events e " +
            "where e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?1 " +
            "order by e.views desc")
    List<Events> getEventsAvailable(LocalDateTime now,
                                    Pageable pageable);

    @Query("select e from Events e " +
            "where e.eventDate > ?1 " +
            "order by e.views desc")
    List<Events> getEventsAll(LocalDateTime now,
                              Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?1 " +
            "order by e.eventDate desc")
    List<Events> getEventsPaidAndAvailableByDate(LocalDateTime now,
                                                 Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.eventDate > ?1 " +
            "order by e.eventDate desc")
    List<Events> getEventsPaidByDate(LocalDateTime now,
                                     Pageable pageable);

    @Query("select e from Events e " +
            "where e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?1 " +
            "order by e.eventDate desc")
    List<Events> getEventsAvailableByDate(LocalDateTime now,
                                          Pageable pageable);

    @Query("select e from Events e " +
            "where e.eventDate > ?1 " +
            "order by e.eventDate desc")
    List<Events> getEventsAllByDate(LocalDateTime now,
                                    Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.categoryId in ?1 " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?2 " +
            "order by e.views desc")
    List<Events> getEventsWitchCatAndPaidAndAvailable(List<Integer> categories,
                                                      LocalDateTime now,
                                                      Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.categoryId in ?1 " +
            "and e.eventDate > ?2 " +
            "order by e.views desc")
    List<Events> getEventsPaidAndCat(List<Integer> categories,
                                     LocalDateTime now,
                                     Pageable pageable);

    @Query("select e from Events e " +
            "where e.categoryId in ?1 " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?2 " +
            "order by e.views desc")
    List<Events> getEventsAvailableAndCat(List<Integer> categories,
                                          LocalDateTime now,
                                          Pageable pageable);

    @Query("select e from Events e " +
            "where e.categoryId in ?1 " +
            "and e.eventDate > ?2 " +
            "order by e.views desc")
    List<Events> getEventsAllAndCat(List<Integer> categories,
                                    LocalDateTime now,
                                    Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.categoryId in ?1 " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsWitchCatAndPaidAndAvailableByDate(List<Integer> categories,
                                                            LocalDateTime now,
                                                            Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.categoryId in ?1 " +
            "and e.eventDate > ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsPaidAndCatByDate(List<Integer> categories,
                                           LocalDateTime now,
                                           Pageable pageable);

    @Query("select e from Events e " +
            "where e.categoryId in ?1 " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsAvailableAndCatByDate(List<Integer> categories,
                                                LocalDateTime now,
                                                Pageable pageable);

    @Query("select e from Events e " +
            "where e.categoryId in ?1 " +
            "and e.eventDate > ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsAllAndCatByDate(List<Integer> categories,
                                          LocalDateTime now,
                                          Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.paid = true " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?2 " +
            "order by e.views desc")
    List<Events> getEventsTextAndAvailableAndPaid(String text,
                                                  LocalDateTime now,
                                                  Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?2 " +
            "order by e.views desc")
    List<Events> getEventsTextAndAvailable(String text,
                                           LocalDateTime now,
                                           Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.paid = true " +
            "and e.eventDate > ?2 " +
            "order by e.views desc")
    List<Events> getEventsTextAndPaid(String text,
                                      LocalDateTime now,
                                      Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.eventDate > ?2 " +
            "order by e.views desc")
    List<Events> getEventsText(String text,
                               LocalDateTime now,
                               Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.paid = true " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsTextAndAvailableAndPaidByDate(String text,
                                                        LocalDateTime now,
                                                        Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsTextAndAvailableByDate(String text,
                                                 LocalDateTime now,
                                                 Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.paid = true " +
            "and e.eventDate > ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsTextAndPaidByDate(String text,
                                            LocalDateTime now,
                                            Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.eventDate > ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsTextByDate(String text,
                                     LocalDateTime now,
                                     Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.categoryId in ?2" +
            "and e.paid = true " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?3 " +
            "order by e.views desc")
    List<Events> getEventsTextAndCategoriesAndAvailableAndPaid(String text,
                                                               List<Integer> categories,
                                                               LocalDateTime now,
                                                               Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.categoryId in ?2" +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?3 " +
            "order by e.views desc")
    List<Events> getEventsTextAndCategoriesAndAvailable(String text,
                                                        List<Integer> categories,
                                                        LocalDateTime now,
                                                        Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.categoryId in ?2 " +
           "and e.paid = true " +
           "and e.eventDate > ?3 " +
           "order by e.views desc")
    List<Events> getEventsTextAndCategoriesAndPaid(String text,
                                                   List<Integer> categories,
                                                   LocalDateTime times,
                                                   Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.categoryId in ?2 " +
            "and e.eventDate > ?3 " +
            "order by e.views desc")
    List<Events> getEventsTextAndCategories(String text,
                                            List<Integer> categories,
                                            LocalDateTime times,
                                            Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.categoryId in ?2 " +
            "and e.paid = true " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?3 " +
            "order by e.eventDate desc")
    List<Events> getEventsTextAndCategoriesAndAvailableAndPaidByDate(String text,
                                                                     List<Integer> categories,
                                                                     LocalDateTime now,
                                                                     Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.categoryId in ?2" +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate > ?3 " +
            "order by e.eventDate desc")
    List<Events> getEventsTextAndCategoriesAndAvailableByDate(String text,
                                                              List<Integer> categories,
                                                              LocalDateTime now,
                                                              Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.categoryId in ?2 " +
            "and e.paid = true " +
            "and e.eventDate > ?3 " +
            "order by e.eventDate desc")
    List<Events> getEventsTextAndCategoriesAndPaidByDate(String text,
                                                         List<Integer> categories,
                                                         LocalDateTime now,
                                                         Pageable pageable);

    @Query("select e from Events e " +
            "where (lower(e.annotation) like ?1 " +
            "or lower(e.description) like ?1) " +
            "and e.categoryId in ?2 " +
            "and e.eventDate > ?3 " +
            "order by e.eventDate desc")
    List<Events> getEventsTextAndCategoriesByDate(String text,
                                                  List<Integer> categories,
                                                  LocalDateTime now,
                                                  Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate between ?1 and ?2 " +
            "order by e.views desc")
    List<Events> getEventsPaidAndAvailable(LocalDateTime start,
                                           LocalDateTime end,
                                           Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.eventDate between ?1 and ?2 " +
            "order by e.views desc")
    List<Events> getEventsPaid(LocalDateTime start,
                               LocalDateTime end,
                               Pageable pageable);

    @Query("select e from Events e " +
            "where e.confirmedRequests < e.participantLimit " +
            "and e.eventDate between ?1 and ?2 " +
            "order by e.views desc")
    List<Events> getEventsAvailable(LocalDateTime start,
                                    LocalDateTime end,
                                    Pageable pageable);

    @Query("select e from Events e " +
            "where e.eventDate between ?1 and ?2 " +
            "order by e.views desc")
    List<Events> getEventsAll(LocalDateTime start,
                              LocalDateTime end,
                              Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate between ?1 and ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsPaidAndAvailableByDate(LocalDateTime start,
                                                 LocalDateTime end,
                                                 Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.eventDate between ?1 and ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsPaidByDate(LocalDateTime start,
                                     LocalDateTime end,
                                     Pageable pageable);

    @Query("select e from Events e " +
            "where e.confirmedRequests < e.participantLimit " +
            "and e.eventDate between ?1 and ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsAvailableByDate(LocalDateTime start,
                                          LocalDateTime end,
                                          Pageable pageable);

    @Query("select e from Events e " +
            "where e.eventDate between ?1 and ?2 " +
            "order by e.eventDate desc")
    List<Events> getEventsAllByDate(LocalDateTime start,
                                    LocalDateTime end,
                                    Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.categoryId in ?1 " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate between ?2 and ?3 " +
            "order by e.views desc")
    List<Events> getEventsWitchCatAndPaidAndAvailable(List<Integer> categories,
                                                      LocalDateTime start,
                                                      LocalDateTime end,
                                                      Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.categoryId in ?1 " +
            "and e.eventDate between ?2 and ?3 " +
            "order by e.views desc")
    List<Events> getEventsPaidAndCat(List<Integer> categories,
                                     LocalDateTime start,
                                     LocalDateTime end,
                                     Pageable pageable);

    @Query("select e from Events e " +
            "where e.categoryId in ?1 " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate between ?2 and ?3 " +
            "order by e.views desc")
    List<Events> getEventsAvailableAndCat(List<Integer> categories,
                                          LocalDateTime start,
                                          LocalDateTime end,
                                          Pageable pageable);

    @Query("select e from Events e " +
            "where e.categoryId in ?1 " +
            "and e.eventDate between ?2 and ?3 " +
            "order by e.views desc")
    List<Events> getEventsAllAndCat(List<Integer> categories,
                                    LocalDateTime start,
                                    LocalDateTime end,
                                    Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.categoryId in ?1 " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate between ?2 and ?3 " +
            "order by e.eventDate desc")
    List<Events> getEventsWitchCatAndPaidAndAvailableByDate(List<Integer> categories,
                                                            LocalDateTime start,
                                                            LocalDateTime end,
                                                            Pageable pageable);

    @Query("select e from Events e " +
            "where e.paid = true " +
            "and e.categoryId in ?1 " +
            "and e.eventDate between ?2 and ?3 " +
            "order by e.eventDate desc")
    List<Events> getEventsPaidAndCatByDate(List<Integer> categories,
                                           LocalDateTime start,
                                           LocalDateTime end,
                                           Pageable pageable);

    @Query("select e from Events e " +
            "where e.categoryId in ?1 " +
            "and e.confirmedRequests < e.participantLimit " +
            "and e.eventDate between ?2 and ?3 " +
            "order by e.eventDate desc")
    List<Events> getEventsAvailableAndCatByDate(List<Integer> categories,
                                                LocalDateTime start,
                                                LocalDateTime end,
                                                Pageable pageable);

    @Query("select e from Events e " +
            "where e.categoryId in ?1 " +
            "and e.eventDate between ?2 and ?3 " +
            "order by e.eventDate desc")
    List<Events> getEventsAllAndCatByDate(List<Integer> categories,
                                          LocalDateTime start,
                                          LocalDateTime end,
                                          Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.paid = true " +
           "and e.confirmedRequests < e.participantLimit " +
           "and e.eventDate between ?2 and ?3 " +
           "order by e.views desc")
    List<Events> getEventsTextAndAvailableAndPaid(String text,
                                                  LocalDateTime start,
                                                  LocalDateTime end,
                                                  Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.confirmedRequests < e.participantLimit " +
           "and e.eventDate between ?2 and ?3 " +
           "order by e.views desc")
    List<Events> getEventsTextAndAvailable(String text,
                                           LocalDateTime start,
                                           LocalDateTime end,
                                           Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.paid = true " +
           "and e.eventDate between ?2 and ?3 " +
           "order by e.views desc")
    List<Events> getEventsTextAndPaid(String text,
                                      LocalDateTime start,
                                      LocalDateTime end,
                                      Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.eventDate between ?2 and ?3 " +
           "order by e.views desc")
    List<Events> getEventsText(String text,
                               LocalDateTime start,
                               LocalDateTime end,
                               Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.paid = true " +
           "and e.confirmedRequests < e.participantLimit " +
           "and e.eventDate between ?2 and ?3 " +
           "order by e.eventDate desc")
    List<Events> getEventsTextAndAvailableAndPaidByDate(String text,
                                                        LocalDateTime start,
                                                        LocalDateTime end,
                                                        Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.confirmedRequests < e.participantLimit " +
           "and e.eventDate between ?2 and ?3 " +
           "order by e.eventDate desc")
    List<Events> getEventsTextAndAvailableByDate(String text,
                                                 LocalDateTime start,
                                                 LocalDateTime end,
                                                 Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.paid = true " +
           "and e.eventDate between ?2 and ?3 " +
           "order by e.eventDate desc")
    List<Events> getEventsTextAndPaidByDate(String text,
                                            LocalDateTime start,
                                            LocalDateTime end,
                                            Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.eventDate between ?2 and ?3 " +
           "order by e.eventDate desc")
    List<Events> getEventsTextByDate(String text,
                                     LocalDateTime start,
                                     LocalDateTime end,
                                     Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.categoryId in ?2" +
           "and e.paid = true " +
           "and e.confirmedRequests < e.participantLimit " +
           "and e.eventDate between ?3 and ?4 " +
           "order by e.views desc")
    List<Events> getEventsTextAndCategoriesAndAvailableAndPaid(String text,
                                                               List<Integer> categories,
                                                               LocalDateTime start,
                                                               LocalDateTime end,
                                                               Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.categoryId in ?2" +
           "and e.confirmedRequests < e.participantLimit " +
           "and e.eventDate between ?3 and ?4 " +
           "order by e.views desc")
    List<Events> getEventsTextAndCategoriesAndAvailable(String text,
                                                        List<Integer> categories,
                                                        LocalDateTime start,
                                                        LocalDateTime end,
                                                        Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.categoryId in ?2" +
           "and e.paid = true " +
           "and e.eventDate between ?3 and ?4 " +
           "order by e.views desc")
    List<Events> getEventsTextAndCategoriesAndPaid(String text,
                                                   List<Integer> categories,
                                                   LocalDateTime start,
                                                   LocalDateTime end,
                                                   Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.categoryId in ?2" +
           "and e.eventDate between ?3 and ?4 " +
           "order by e.views desc")
    List<Events> getEventsTextAndCategories(String text,
                                            List<Integer> categories,
                                            LocalDateTime start,
                                            LocalDateTime end,
                                            Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.categoryId in ?2" +
           "and e.paid = true " +
           "and e.confirmedRequests < e.participantLimit " +
           "and e.eventDate between ?3 and ?4 " +
           "order by e.eventDate desc")
    List<Events> getEventsTextAndCategoriesAndAvailableAndPaidByDate(String text,
                                                                     List<Integer> categories,
                                                                     LocalDateTime start,
                                                                     LocalDateTime end,
                                                                     Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.categoryId in ?2" +
           "and e.confirmedRequests < e.participantLimit " +
           "and e.eventDate between ?3 and ?4 " +
           "order by e.eventDate desc")
    List<Events> getEventsTextAndCategoriesAndAvailableByDate(String text,
                                                              List<Integer> categories,
                                                              LocalDateTime start,
                                                              LocalDateTime end,
                                                              Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.categoryId in ?2 " +
           "and e.paid = true " +
           "and e.eventDate between ?3 and ?4 " +
           "order by e.eventDate desc")
    List<Events> getEventsTextAndCategoriesAndPaidByDate(String text,
                                                         List<Integer> categories,
                                                         LocalDateTime start,
                                                         LocalDateTime end,
                                                         Pageable pageable);

    @Query("select e from Events e " +
           "where (lower(e.annotation) like ?1 " +
           "or lower(e.description) like ?1) " +
           "and e.categoryId in ?2 " +
           "and e.eventDate between ?3 and ?4 " +
           "order by e.eventDate desc")
    List<Events> getEventsTextAndCategoriesByDate(String text,
                                                  List<Integer> categories,
                                                  LocalDateTime start,
                                                  LocalDateTime end,
                                                  Pageable pageable);

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

    @Query("select e from Events e " +
           "where e.id in ?1")
    List<Events> findAll(List<Integer> eventsIdList);
}
