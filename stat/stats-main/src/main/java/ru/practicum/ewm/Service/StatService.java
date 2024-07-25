package ru.practicum.ewm.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.Dto.model.Hit;
import ru.practicum.ewm.Dto.model.dto.HitMapper;
import ru.practicum.ewm.Dto.model.dto.NewHit;
import ru.practicum.ewm.error.exception.BadRequestException;
import ru.practicum.ewm.repository.StatRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {
    private final StatRepository repository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void saveHit(NewHit newHit) {
        Hit hit = HitMapper.toHit(newHit);
        repository.save(hit);
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
                return repository.getStats(startTime, endTime, uris);
            }
        }
    }
}
