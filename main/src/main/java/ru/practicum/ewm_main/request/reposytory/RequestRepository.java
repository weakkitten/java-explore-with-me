package ru.practicum.ewm_main.request.reposytory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.request.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {
}
