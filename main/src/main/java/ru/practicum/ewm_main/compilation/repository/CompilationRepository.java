package ru.practicum.ewm_main.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Integer, Compilation> {
}
