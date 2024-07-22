package ru.practicum.ewm_main.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_main.categories.repository.CategoriesRepository;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository repository;

    public ResponseEntity<Object> getCategories(int from, int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(repository.findAll(PageRequest.of(from / size, size)).toList());
    }

    public ResponseEntity<Object> getCategoriesById(int catId) {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(catId));
    }
}
