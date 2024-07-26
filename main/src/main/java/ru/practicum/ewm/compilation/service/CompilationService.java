package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.dto.CompilationMapper;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Events;
import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.event.model.dto.EventsMapper;
import ru.practicum.ewm.event.repository.CompilationsEventsRepository;
import ru.practicum.ewm.event.repository.EventsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationService {
    private final CompilationRepository repository;
    private final CompilationsEventsRepository compilationsEventsRepository;
    private final EventsRepository eventsRepository;

    public ResponseEntity<Object> getCompilations(Boolean pinned, int from, int size) {
        List<Compilation> compilationsEventsList;

        if (pinned == null) {
            compilationsEventsList = repository.findAll(PageRequest.of(from / size, size)).toList();
        } else {
            compilationsEventsList = repository.findByPinned(pinned, PageRequest.of(from / size, size));
        }
        List<CompilationDto> compilationDtos = new ArrayList<>();
        for (Compilation compilation : compilationsEventsList) {
            List<Integer> eventsIdsList = compilationsEventsRepository.getEventIds(compilation.getId());
            List<Events> eventsList = eventsRepository.findAll(eventsIdsList);
            List<EventShortDto> eventShortDtoList = new ArrayList<>();
            for (Events events : eventsList) {
                eventShortDtoList.add(EventsMapper.toEventShortDto(events));
            }
            CompilationDto compilationDto = CompilationMapper.toCompilationDto(compilation, eventShortDtoList);
            compilationDtos.add(compilationDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(compilationDtos);
    }

    public ResponseEntity<Object> getCompilationsById(int compId) {
        List<Integer> eventsIdsList = compilationsEventsRepository.getEventIds(compId);
        List<Events> eventsList = eventsRepository.findAll(eventsIdsList);
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (Events events : eventsList) {
            eventShortDtoList.add(EventsMapper.toEventShortDto(events));
        }
        Compilation compilation = repository.findById(compId).get();
        CompilationDto compilationDto = CompilationMapper.toCompilationDto(compilation, eventShortDtoList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(compilationDto);
    }
}
