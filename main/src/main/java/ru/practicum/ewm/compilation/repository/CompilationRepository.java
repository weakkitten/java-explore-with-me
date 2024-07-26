package ru.practicum.ewm.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    Compilation findByPinnedAndTitle(boolean pinned, String title);

    List<Compilation> findByPinned(boolean pinned, Pageable pageable);
}
