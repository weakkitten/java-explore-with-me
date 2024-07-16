package ru.practicum.ewm_main.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_main.categories.repository.CategoriesRepository;
import ru.practicum.ewm_main.compilation.repository.CompilationRepository;
import ru.practicum.ewm_main.event.repository.EventsRepository;
import ru.practicum.ewm_main.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;
    private final CompilationRepository compilationRepository;
    private final CategoriesRepository categoriesRepository;

    public void addNewCategories() {

    }

    public void deleteCategories(int catId) {

    }

    public void updateCategories(int catId) {

    }
}
