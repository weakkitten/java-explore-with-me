package ru.practicum.ewm_main.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.categories.model.Categories;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    public Categories findByName(String name);
}
