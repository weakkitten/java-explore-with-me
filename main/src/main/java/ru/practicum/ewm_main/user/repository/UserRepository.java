package ru.practicum.ewm_main.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.user.model.User;

public interface UserRepository extends JpaRepository<Integer, User> {
}
