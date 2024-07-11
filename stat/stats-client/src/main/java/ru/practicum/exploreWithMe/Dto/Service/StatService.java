package ru.practicum.exploreWithMe.Dto.Service;

import ru.practicum.exploreWithMe.Dto.model.ViewStatsInterface;
import ru.practicum.exploreWithMe.Dto.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.Dto.model.Hit;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {
    private final StatRepository repository;

    @Transactional
    public void saveHit(Hit hit) {
        repository.save(hit);
    }

    public List<ViewStatsInterface> getStat(String start,
                                   String end,
                                   List<String> uris,
                                   boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);
        if (uris == null) {
            if (unique) {
                return repository.getStatsUniqueWithoutUris(startTime, endTime);
            } else {
                return repository.getStatsWithoutUris(startTime, endTime);
            }
        } else {
            if (unique) {
                return repository.getStatsUnique(startTime, endTime, uris);
            } else {
                System.out.println("Список - " + List.of(uris));
                return repository.getStats(startTime, endTime, uris);
            }
        }
    }
}
