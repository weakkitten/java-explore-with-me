package ru.practicum.ewm_main.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.categories.model.Categories;

public interface CategoriesRepository extends JpaRepository<Integer, Categories> {
}
