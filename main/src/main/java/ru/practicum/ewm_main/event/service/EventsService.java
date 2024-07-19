package ru.practicum.ewm_main.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_main.error.exception.BadRequestException;
import ru.practicum.ewm_main.error.exception.NotFoundException;
import ru.practicum.ewm_main.event.model.Events;
import ru.practicum.ewm_main.event.model.Views;
import ru.practicum.ewm_main.event.model.dto.EventFullDto;
import ru.practicum.ewm_main.event.model.dto.EventsMapper;
import ru.practicum.ewm_main.event.repository.EventsRepository;
import ru.practicum.ewm_main.event.repository.ViewsRepository;
import ru.practicum.ewm_main.utility.State;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventsService {
    private final EventsRepository repository;
    private final ViewsRepository viewsRepository;

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
        if (LocalDateTime.parse(rangeEnd, formatter).isBefore(LocalDateTime.parse(rangeStart, formatter))) {
            throw new BadRequestException("Некорректный отрезок времени");
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
        return null;
    }

    public ResponseEntity<Object> getEventsById(int id, String ip, String requestUri) {
        if (repository.findById(id).get().getState() != State.PUBLISHED) {
            throw new NotFoundException("Не найдено");
        }
        if (viewsRepository.findByEventIdAndIp(id, ip).isEmpty()) {
            Events events = repository.findById(id).get();
            events.setViews(events.getViews() + 1);
            repository.save(events);
            Views views = Views.builder()
                    .eventId(id)
                    .ip(ip)
                    .build();
            viewsRepository.save(views);
        }
        /*            RestTemplate template = new RestTemplate();
            Hit hit = Hit.builder()
                    .app("ewm-main")
                    .uri(requestUri)
                    .ip(ip)
                    .timestamp(LocalDateTime.now())
                    .build();
            template.postForEntity("http://localhost:9090", hit, Void.class);*/
        Events events = repository.findById(id).get();
        EventFullDto fullDto = EventsMapper.toEventFullDto(events);
        return ResponseEntity.status(HttpStatus.OK).body(fullDto);
    }
}
