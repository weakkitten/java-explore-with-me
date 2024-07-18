package ru.practicum.ewm_main.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.user.model.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findById(int userId, Pageable pageable);
    User findByNameAndEmail(String name, String email);
}
