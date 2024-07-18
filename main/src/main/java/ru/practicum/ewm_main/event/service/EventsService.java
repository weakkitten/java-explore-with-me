package ru.practicum.ewm_main.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_main.event.repository.EventsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventsService {
    private final EventsRepository repository;

    public ResponseEntity<Object> getEvents(String text,
                                            List<Integer> categories,
                                            boolean paid,
                                            String rangeStart,
                                            String rangeEnd,
                                            boolean onlyAvailable,
                                            String sort,
                                            int from,
                                            int size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (sort == null) {
            return ResponseEntity.ok(List.of());
        }
        if (rangeEnd == null) {
            LocalDateTime time = LocalDateTime.now();
            if (sort.equals("EVENT_DATE")) {
                if (onlyAvailable) {
                    return ResponseEntity.ok(repository.getEventsAvailableOrderByEventDateWithoutTimes(text,
                            categories,
                            paid,
                            time,
                            PageRequest.of(from / size, size)));
                } else {
                    return ResponseEntity.ok(repository.getEventsAllOrderByEventDateWithoutTimes(text,
                            categories,
                            paid,
                            time,
                            PageRequest.of(from / size, size)));
                }
            } else if (sort.equals("VIEWS")) {
                if (onlyAvailable) {
                    return ResponseEntity.ok(repository.getEventsAvailableOrderByViewsWithoutTimes(text,
                            categories,
                            paid,
                            time,
                            PageRequest.of(from / size, size)));
                } else {
                    return ResponseEntity.ok(repository.getEventsAllOrderByViewsWithoutTimes(text,
                            categories,
                            paid,
                            time,
                            PageRequest.of(from / size, size)));
                }
            }
        } else {
            if (sort.equals("EVENT_DATE")) {
                if (onlyAvailable) {
                    return ResponseEntity.ok(repository.getEventsAvailableOrderByEventDate(text,
                            categories,
                            paid,
                            LocalDateTime.parse(rangeStart, formatter),
                            LocalDateTime.parse(rangeEnd, formatter),
                            PageRequest.of(from / size, size)));
                } else {
                    return ResponseEntity.ok(repository.getEventsAllOrderByEventDate(text,
                            categories,
                            paid,
                            LocalDateTime.parse(rangeStart, formatter),
                            LocalDateTime.parse(rangeEnd, formatter),
                            PageRequest.of(from / size, size)));
                }
            } else if (sort.equals("VIEWS")) {
                if (onlyAvailable) {
                    return ResponseEntity.ok(repository.getEventsAvailableOrderByViews(text,
                            categories,
                            paid,
                            LocalDateTime.parse(rangeStart, formatter),
                            LocalDateTime.parse(rangeEnd, formatter),
                            PageRequest.of(from / size, size)));
                } else {
                    return ResponseEntity.ok(repository.getEventsAllOrderByViews(text,
                            categories,
                            paid,
                            LocalDateTime.parse(rangeStart, formatter),
                            LocalDateTime.parse(rangeEnd, formatter),
                            PageRequest.of(from / size, size)));
                }
            }
        }
        System.out.println("Не верю..");
        return null;
    }

    public ResponseEntity<Object> getEventsById(int id) {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }
}
