package ru.practicum.ewm_main.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_main.event.repository.EventsRepository;

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
        return null;
    }

    public ResponseEntity<Object> getEventsById(int id) {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }
}
