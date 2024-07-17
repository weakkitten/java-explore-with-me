package ru.practicum.ewm_main.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_main.categories.model.Categories;
import ru.practicum.ewm_main.categories.model.dto.CategoriesMapper;
import ru.practicum.ewm_main.categories.model.dto.NewCategoryDto;
import ru.practicum.ewm_main.categories.repository.CategoriesRepository;
import ru.practicum.ewm_main.compilation.model.Compilation;
import ru.practicum.ewm_main.compilation.model.dto.CompilationMapper;
import ru.practicum.ewm_main.compilation.model.dto.NewCompilationDto;
import ru.practicum.ewm_main.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.ewm_main.compilation.repository.CompilationRepository;
import ru.practicum.ewm_main.error.exception.BadRequestException;
import ru.practicum.ewm_main.event.model.CompilationsEvents;
import ru.practicum.ewm_main.event.model.Events;
import ru.practicum.ewm_main.event.model.dto.EventsMapper;
import ru.practicum.ewm_main.event.model.dto.UpdateEventAdminRequest;
import ru.practicum.ewm_main.event.repository.CompilationsEventsRepository;
import ru.practicum.ewm_main.event.repository.EventsRepository;
import ru.practicum.ewm_main.request.repository.RequestRepository;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.model.dto.UserDto;
import ru.practicum.ewm_main.user.model.dto.UserMapper;
import ru.practicum.ewm_main.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;
    private final CompilationRepository compilationRepository;
    private final CategoriesRepository categoriesRepository;
    private final RequestRepository requestRepository;
    private final CompilationsEventsRepository compilationsEventsRepository;

    public ResponseEntity<Object> addNewCategories(NewCategoryDto dto) {
        Categories categories = CategoriesMapper.toCategories(dto);
        categoriesRepository.save(categories);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoriesMapper.toCategoryDto(categoriesRepository.findByName(categories.getName())));
    }

    public ResponseEntity<Object> deleteCategories(int catId) {
        categoriesRepository.deleteById(catId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    public void updateCategories(int catId, NewCategoryDto dto) {
        if (categoriesRepository.findByName(dto.getName()) != null) {
            throw new BadRequestException("Некорректный запрос");
        }
        Categories categories = CategoriesMapper.toCategories(dto);
        categories.setId(catId);
        categoriesRepository.save(categories);
    }

    public void getEvents() {

    }

    public void updateEvents(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Events events = eventsRepository.findById(eventId).get();
        Events updateEvents = EventsMapper.updateEvents(events, updateEventAdminRequest);
        eventsRepository.save(updateEvents);
    }

    public List<User> getUsers(int userId, int from, int size) {
        return userRepository.findById(userId, PageRequest.of(from > 0 ? from / size : 0, size));
    }

    public void addNewUser(UserDto dto) {
        User user = UserMapper.toUser(dto);
        userRepository.save(user);
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    public void addNewCompilation(NewCompilationDto dto) {
        List<Integer> eventList = dto.getEvents();
        Compilation compilation = CompilationMapper.toCompilation(dto);
        compilationRepository.save(compilation);
        for (Integer eventId : eventList) {
            CompilationsEvents compilationsEvents = CompilationsEvents.builder()
                    .eventId(eventId)
                    .compilationId(compilation.getId())
                    .build();
            compilationsEventsRepository.save(compilationsEvents);
        }
    }

    public void deleteCompilations(int compId) {
        List<CompilationsEvents> compilationsEventsList = compilationsEventsRepository.findByCompilationId(compId);
        compilationsEventsRepository.deleteAll(compilationsEventsList);
        compilationRepository.deleteById(compId);
    }

    public void updateCompilation(int compId, UpdateCompilationRequest updateCompilationRequest) {
        List<Integer> eventList = updateCompilationRequest.getEvents();
        List<CompilationsEvents> compilationsEventsList = compilationsEventsRepository.findByCompilationId(compId);
        compilationsEventsRepository.deleteAll(compilationsEventsList);
        for (Integer eventId : eventList) {
            CompilationsEvents compilationsEvents = CompilationsEvents.builder()
                    .eventId(eventId)
                    .compilationId(compId)
                    .build();
            compilationsEventsRepository.save(compilationsEvents);
        }
        Compilation compilation = compilationRepository.findById(compId).get();
        Compilation compilationUpdate = CompilationMapper.updateCompilation(updateCompilationRequest,
                                                                            compilation);
        compilationRepository.save(compilationUpdate);
    }
}
