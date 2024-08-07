package ru.practicum.ewm.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.categories.repository.CategoriesRepository;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository repository;

    public ResponseEntity<Object> getCategories(int from, int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(repository.findAll(PageRequest.of(from / size, size)).toList());
    }

    public ResponseEntity<Object> getCategoriesById(int catId) {
        if (repository.findById(catId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(catId));
    }
}
