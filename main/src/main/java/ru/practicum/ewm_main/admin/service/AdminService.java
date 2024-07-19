package ru.practicum.ewm_main.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_main.categories.model.Categories;
import ru.practicum.ewm_main.categories.model.dto.CategoriesMapper;
import ru.practicum.ewm_main.categories.model.dto.CategoryDto;
import ru.practicum.ewm_main.categories.model.dto.NewCategoryDto;
import ru.practicum.ewm_main.categories.repository.CategoriesRepository;
import ru.practicum.ewm_main.compilation.model.Compilation;
import ru.practicum.ewm_main.compilation.model.dto.CompilationDto;
import ru.practicum.ewm_main.compilation.model.dto.CompilationMapper;
import ru.practicum.ewm_main.compilation.model.dto.NewCompilationDto;
import ru.practicum.ewm_main.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.ewm_main.compilation.repository.CompilationRepository;
import ru.practicum.ewm_main.error.exception.BadRequestException;
import ru.practicum.ewm_main.event.model.CompilationsEvents;
import ru.practicum.ewm_main.event.model.Events;
import ru.practicum.ewm_main.event.model.Location;
import ru.practicum.ewm_main.event.model.dto.EventFullDto;
import ru.practicum.ewm_main.event.model.dto.EventShortDto;
import ru.practicum.ewm_main.event.model.dto.EventsMapper;
import ru.practicum.ewm_main.event.model.dto.UpdateEventAdminRequest;
import ru.practicum.ewm_main.event.repository.CompilationsEventsRepository;
import ru.practicum.ewm_main.event.repository.EventsRepository;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.model.dto.NewUserDto;
import ru.practicum.ewm_main.user.model.dto.UserDto;
import ru.practicum.ewm_main.user.model.dto.UserMapper;
import ru.practicum.ewm_main.user.model.dto.UserShortDto;
import ru.practicum.ewm_main.user.repository.UserRepository;
import ru.practicum.ewm_main.utility.State;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;
    private final CompilationRepository compilationRepository;
    private final CategoriesRepository categoriesRepository;
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

    public ResponseEntity<Object> updateCategories(int catId, NewCategoryDto dto) {
        if (categoriesRepository.findByName(dto.getName()) != null) {
            throw new BadRequestException("Некорректный запрос");
        }
        Categories categories = CategoriesMapper.toCategories(dto);
        categories.setId(catId);
        categoriesRepository.save(categories);
        CategoryDto categoryDto = CategoriesMapper.toCategoryDto(categoriesRepository.findById(catId).get());
        return ResponseEntity.ok(categoryDto);
    }

    public ResponseEntity<Object> getEvents(List<Integer> users,
                                            List<String> states,
                                            List<Integer> categories,
                                            String rangeStart,
                                            String rangeEnd,
                                            int from,
                                            int size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<State> stateList = new ArrayList<>();
        for (String str : states) {
            stateList.add(State.valueOf(str));
        }
        List<Events> eventsList = eventsRepository.getEventsWithUsersAndStatesAndCategories(users,
                    stateList,
                    categories,
                    LocalDateTime.parse(rangeStart, formatter),
                    LocalDateTime.parse(rangeEnd, formatter),
                    PageRequest.of(from / size, size));
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (Events events : eventsList) {
            eventFullDtoList.add(EventsMapper.toEventFullDto(events));
        }
        return ResponseEntity.ok(eventFullDtoList);
    }

    public ResponseEntity<Object> updateEvents(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Events events = eventsRepository.findById(eventId).get();
        System.out.println("До отмены - " + events);
        Events updateEvents = EventsMapper.updateForAdmin(events, updateEventAdminRequest);
        eventsRepository.save(updateEvents);
        System.out.println("После отмены - " + eventsRepository.findById(eventId).get());
        EventFullDto eventFullDtoDto = EventsMapper.toEventFullDto(eventsRepository.findById(eventId).get());
        return ResponseEntity.ok(eventFullDtoDto);
    }

    public ResponseEntity<Object> getUsers(int userId, int from, int size) {
        return ResponseEntity.ok(userRepository.findById(userId, PageRequest.of(from > 0 ? from / size : 0, size)));
    }

    public ResponseEntity<Object> addNewUser(NewUserDto dto) {
        User user = UserMapper.toUser(dto);
        userRepository.save(user);
        UserDto userDto = UserMapper.toUserDto(userRepository.findByNameAndEmail(user.getName(),
                user.getEmail()));
        return ResponseEntity.ok(userDto);
    }

    public ResponseEntity<Object> deleteUser(int userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Object> addNewCompilation(NewCompilationDto dto) {
        List<Integer> eventList = dto.getEvents();
        Compilation compilation = CompilationMapper.toCompilation(dto);
        compilationRepository.save(compilation);
        List<CompilationsEvents> compilationsEventsList = new ArrayList<>();
        for (Integer eventId : eventList) {
            CompilationsEvents compilationsEvents = CompilationsEvents.builder()
                    .eventId(eventId)
                    .compilationId(compilation.getId())
                    .build();
            compilationsEventsList.add(compilationsEvents);
        }
        compilationsEventsRepository.saveAll(compilationsEventsList);
        return ResponseEntity.ok(null);
    }

    public ResponseEntity<Object> deleteCompilations(int compId) {
        List<CompilationsEvents> compilationsEventsList = compilationsEventsRepository.findByCompilationId(compId);
        compilationsEventsRepository.deleteAll(compilationsEventsList);
        compilationRepository.deleteById(compId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Object> updateCompilation(int compId, UpdateCompilationRequest updateCompilationRequest) {
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
        CompilationDto compilationDto = CompilationMapper.toCompilationDto(compilationRepository.findById(compId).get(),
                null);
        return ResponseEntity.ok(compilationDto);
    }
}
