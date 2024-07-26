package ru.practicum.ewm.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.categories.model.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    public Categories findByName(String name);
}
