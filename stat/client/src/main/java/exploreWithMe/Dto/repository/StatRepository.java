package exploreWithMe.Dto.repository;

import exploreWithMe.Dto.model.Hit;
import exploreWithMe.Dto.model.ViewStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface StatRepository extends JpaRepository<Hit, Integer> {
    public Hit findByAppAndUriAndIp(String app, String uri, String ip);
    @Query("select h.app, h.uri, count(h) from Hit h " +
           "where (h.timestamp > ?1 and h.timestamp < ?2) " +
           "and h.uri in ?3")
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uri);
    @Query("select h.app, h.uri, count(distinct h) from Hit h " +
            "where (h.timestamp > ?1 and h.timestamp < ?2) " +
            "and h.uri in ?3")
    public List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uri);
}
