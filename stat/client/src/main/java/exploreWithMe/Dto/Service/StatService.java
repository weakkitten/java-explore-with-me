package exploreWithMe.Dto.Service;

import exploreWithMe.Dto.model.Hit;
import exploreWithMe.Dto.model.ViewStats;
import exploreWithMe.Dto.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {
    private final StatRepository repository;

    @Transactional
    public void saveHit(Hit hit) {
        repository.save(hit);
    }

    public List<ViewStats> getStat(LocalDateTime start,
                                   LocalDateTime end,
                                   List<String> uris,
                                   boolean unique) {
        if (uris == null) {
            if (unique) {
                return repository.getStatsWithoutUris(start, end);
            } else {
                return repository.getStatsUniqueWithoutUris(start, end);
            }
        } else {
            if (unique) {
                return repository.getStats(start, end, uris);
            } else {
                return repository.getStatsUnique(start, end, uris);
            }
        }
    }
}
