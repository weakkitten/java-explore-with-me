package ru.practicum.ewm_main.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.admin.model.Admin;

public interface AdminRepository extends JpaRepository<Integer, Admin> {
}
