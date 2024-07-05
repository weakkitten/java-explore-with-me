package exploreWithMe.Dto.Service;

import exploreWithMe.Dto.model.Hit;
import exploreWithMe.Dto.model.ViewStats;
import exploreWithMe.Dto.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {
    private final StatRepository repository;

    @Transactional
    public void saveHit(Hit hit) {
        repository.save(hit);
    }

    public List<Object> getStat(String start,
                                   String end,
                                   List<String> uris,
                                   boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);
        if (uris == null) {
            if (unique) {
                return repository.getStatsWithoutUris(startTime, endTime);
            } else {
                return repository.getStatsUniqueWithoutUris(startTime, endTime);
            }
        } else {
            System.out.println("Список - " + uris.get(0));
            if (unique) {
                return repository.getStats(startTime, endTime, List.of(uris.get(0)));
            } else {
                return repository.getStatsUnique(startTime, endTime, List.of(uris.get(0)));
            }
        }
    }
}
