package ru.practicum.ewm_main.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_main.compilation.model.Compilation;
import ru.practicum.ewm_main.compilation.repository.CompilationRepository;

@Service
@RequiredArgsConstructor
public class CompilationService {
    private final CompilationRepository repository;

    public ResponseEntity<Object> getCompilations(Boolean pinned, int from, int size) {
        if (pinned == null) {
            return ResponseEntity.ok(repository.findAll(PageRequest.of(from / size, size)).toList());
        }
        return ResponseEntity.status(HttpStatus.OK)
                              .body(repository.findByPinned(pinned,
                                                            PageRequest.of(from / size, size)));
    }

    public ResponseEntity<Object> getCompilationsById(int compId) {
        Compilation compilation = repository.findById(compId).get();
        return ResponseEntity.status(HttpStatus.OK)
                .body(compilation);
    }
}
