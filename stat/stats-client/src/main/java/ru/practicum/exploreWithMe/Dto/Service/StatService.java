package ru.practicum.exploreWithMe.Dto.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.exploreWithMe.Dto.error.exception.BadRequestException;
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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public ResponseEntity<Hit> saveHit(Hit hit) {
        repository.save(hit);
        Hit hitDto = repository.findByIpAndTimes(hit.getIp(), hit.getTimestamp());
        return ResponseEntity.status(HttpStatus.CREATED).body(hitDto);
    }

    public Object getStat(String start,
                                   String end,
                                   List<String> uris,
                                   boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);
        if (startTime.isAfter(endTime)) {
            throw new BadRequestException("Некорректное время");
        }
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
