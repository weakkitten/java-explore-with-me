package ru.practicum.ewm_main.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.user.model.User;

import java.awt.print.Pageable;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByIdPageable(int userId, Pageable pageable);
}
