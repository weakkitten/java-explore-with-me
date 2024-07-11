package ru.practicum.exploreWithMe.Dto.repository;

import ru.practicum.exploreWithMe.Dto.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.Dto.model.ViewStatsInterface;

import java.time.LocalDateTime;
import java.util.List;


public interface StatRepository extends JpaRepository<Hit, Integer> {
    @Query(value = "select s.app, s.uri, count(s.uri) as hits from stats as s " +
           "where s.times between ?1 and ?2 " +
           "and s.uri in ?3 " +
            "group by s.uri " +
            "order by hits desc", nativeQuery = true)
    List<ViewStatsInterface> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);
    @Query(value = "select distinct s.app, s.uri, count(distinct s.uri) as hits from stats as s " +
            "where s.times between ?1 and ?2 " +
            "and s.uri in (?3) " +
            "group by s.uri " +
            "order by hits desc", nativeQuery = true)
    List<ViewStatsInterface> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
    @Query(value = "select s.app, s.uri, count(s.uri) as hits from stats as s " +
            "where s.times between ?1 and ?2 " +
            "group by s.uri", nativeQuery = true)
    List<ViewStatsInterface> getStatsWithoutUris(LocalDateTime start, LocalDateTime end);
    @Query(value = "select distinct s.app, s.uri, count(distinct s.uri) as hits from stats as s " +
            "where s.times between ?1 and ?2 " +
            "group by s.uri", nativeQuery = true)
    List<ViewStatsInterface> getStatsUniqueWithoutUris(LocalDateTime start, LocalDateTime end);
}
