package exploreWithMe.Dto.repository;

import exploreWithMe.Dto.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface StatRepository extends JpaRepository<Hit, Integer> {
    public Hit findByAppAndUriAndIp(String app, String uri, String ip);
    @Query("select h.app, h.uri, count(h) from Hit h " +
           "where (h.timestamp > ?1 and h.timestamp < ?2) " +
           "and h.uri in (?3) " +
            "group by h.uri ")
    public List<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);
    @Query("select h.app, h.uri, count(distinct h) from Hit h " +
            "where (h.timestamp > ?1 and h.timestamp < ?2) " +
            "and h.uri in (?3) " +
            "group by h.uri ")
    public List<Object> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
    @Query("select h.app, h.uri, count(h) from Hit h " +
            "where h.timestamp between ?1 and ?2 " +
            "group by h.uri ")
    public List<Object> getStatsWithoutUris(LocalDateTime start, LocalDateTime end);
    @Query("select h.app, h.uri, count(distinct h) from Hit h " +
            "where h.timestamp between ?1 and ?2 " +
            "group by h.uri ")
    public List<Object> getStatsUniqueWithoutUris(LocalDateTime start, LocalDateTime end);

}
