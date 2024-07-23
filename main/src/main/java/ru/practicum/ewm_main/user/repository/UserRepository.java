package ru.practicum.ewm_main.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm_main.user.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u " +
           "where u.id in ?1")
    List<User> findById(List<Integer> userId, Pageable pageable);
    User findByNameAndEmail(String name, String email);
    Optional<User> findByEmail(String email);
}
