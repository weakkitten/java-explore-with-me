package ru.practicum.ewm_main.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import ru.practicum.ewm_main.error.exception.ConflictException;
import ru.practicum.ewm_main.error.exception.NotFoundException;
import ru.practicum.ewm_main.event.model.CompilationsEvents;
import ru.practicum.ewm_main.event.model.Events;
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
import ru.practicum.ewm_main.utility.StateActionAdmin;

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

    @Transactional
    public ResponseEntity<Object> addNewCategories(NewCategoryDto dto) {
        Categories categories = CategoriesMapper.toCategories(dto);
        if (categoriesRepository.findByName(dto.getName()) != null) {
            throw new ConflictException("Категория с таким именем уже существует");
        }
        categoriesRepository.save(categories);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoriesMapper.toCategoryDto(categoriesRepository.findByName(categories.getName())));
    }

    @Transactional
    public ResponseEntity<Object> deleteCategories(int catId) {
        if (categoriesRepository.findById(catId).isEmpty()) {
            throw new NotFoundException("Не найдено категории");
        }
        System.out.println("Покажи что тут используется" + eventsRepository.findByCategoryId(catId).get().isEmpty());
        if (!eventsRepository.findByCategoryId(catId).get().isEmpty()) {
            throw new ConflictException("Попытка удаления используемой категории");
        }
        categoriesRepository.deleteById(catId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    public ResponseEntity<Object> updateCategories(int catId, NewCategoryDto dto) {
        if (categoriesRepository.findByName(dto.getName()) != null) {
            if (!categoriesRepository.findById(catId).get().getName().equals(dto.getName())) {
                throw new ConflictException("Попытка изменить имя категории на существующее");
            }
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

        List<Events> eventsList;
        if (rangeEnd == null) {
            if (users == null) {
                if (states == null) {
                    if (categories == null) {
                        eventsList = eventsRepository.getEventsWithoutTimes(LocalDateTime.now(),
                                                                            PageRequest.of(from / size, size));
                    } else {
                        eventsList = eventsRepository.getEventsWithCategory(categories,
                                                                            LocalDateTime.now(),
                                                                            PageRequest.of(from / size, size));
                    }
                } else {
                    List<State> stateList = new ArrayList<>();
                    for (String str : states) {
                        stateList.add(State.valueOf(str));
                    }
                    if (categories == null) {
                        eventsList = eventsRepository.getEventsWithState(stateList,
                                                                         LocalDateTime.now(),
                                                                         PageRequest.of(from / size, size));
                    } else {
                        eventsList = eventsRepository.getEventsWithStateAndCategories(stateList,
                                categories,
                                LocalDateTime.now(),
                                PageRequest.of(from / size, size));
                    }
                }
            } else {
                if (states == null) {
                    if (categories == null) {//с пользователями
                        eventsList = eventsRepository.getEventsWithUsers(users,
                                LocalDateTime.now(),
                                PageRequest.of(from / size, size));
                    } else {
                        eventsList = eventsRepository.getEventsWithUsersAndCategories(users,
                                categories,
                                LocalDateTime.now(),
                                PageRequest.of(from / size, size));
                    }
                } else {
                    List<State> stateList = new ArrayList<>();
                    for (String str : states) {
                        stateList.add(State.valueOf(str));
                    }
                    if (categories == null) {
                        eventsList = eventsRepository.getEventsWithUsersAndState(users,
                                stateList,
                                LocalDateTime.now(),
                                PageRequest.of(from / size, size));
                    } else {
                        eventsList = eventsRepository.getEventsWithUsersAndStatesAndCategoriesWithoutTimes(users,
                                stateList,
                                categories,
                                LocalDateTime.now(),
                                PageRequest.of(from / size, size));
                    }
                }
            }
        } else {
            if (users == null) {
                if (states == null) {
                    if (categories == null) {
                        eventsList = eventsRepository.getEventsWithTimes(LocalDateTime.parse(rangeStart, formatter),
                                LocalDateTime.parse(rangeEnd, formatter),
                                PageRequest.of(from / size, size));
                    } else {
                        eventsList = eventsRepository.getEventsWithCategoryAndTimes(categories,
                                LocalDateTime.parse(rangeStart, formatter),
                                LocalDateTime.parse(rangeEnd, formatter),
                                PageRequest.of(from / size, size));
                    }
                } else {
                    List<State> stateList = new ArrayList<>();
                    for (String str : states) {
                        stateList.add(State.valueOf(str));
                    }
                    if (categories == null) {
                        System.out.println("Мы сюда попадаем?");
                        eventsList = eventsRepository.getEventsWithStateAndTimes(stateList,
                                LocalDateTime.parse(rangeStart, formatter),
                                LocalDateTime.parse(rangeEnd, formatter),
                                PageRequest.of(from / size, size));
                    } else {
                        eventsList = eventsRepository.getEventsWithStateAndCategoriesAndTimes(stateList,
                                categories,
                                LocalDateTime.parse(rangeStart, formatter),
                                LocalDateTime.parse(rangeEnd, formatter),
                                PageRequest.of(from / size, size));
                    }
                }
            } else {
                if (states == null) {
                    if (categories == null) {
                        eventsList = eventsRepository.getEventsWithUsersAndTimes(users,
                                LocalDateTime.parse(rangeStart, formatter),
                                LocalDateTime.parse(rangeEnd, formatter),
                                PageRequest.of(from / size, size));
                    } else {
                        eventsList = eventsRepository.getEventsWithUsersAndTimesAndCategories(users,
                                categories,
                                LocalDateTime.parse(rangeStart, formatter),
                                LocalDateTime.parse(rangeEnd, formatter),
                                PageRequest.of(from / size, size));
                    }
                } else {
                    List<State> stateList = new ArrayList<>();
                    for (String str : states) {
                        stateList.add(State.valueOf(str));
                    }
                    if (categories == null) {
                        eventsList = eventsRepository.getEventsWithUsersAndStatesAndTimes(users,
                                stateList,
                                LocalDateTime.parse(rangeStart, formatter),
                                LocalDateTime.parse(rangeEnd, formatter),
                                PageRequest.of(from / size, size));
                    } else {
                        eventsList = eventsRepository.getEventsWithUsersAndStatesAndCategoriesAndTimes(users,
                                stateList,
                                categories,
                                LocalDateTime.parse(rangeStart, formatter),
                                LocalDateTime.parse(rangeEnd, formatter),
                                PageRequest.of(from / size, size));
                        System.out.println(eventsList.size());
                    }
                }
            }
        }
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        if (!eventsList.isEmpty()) {
            for (Events events : eventsList) {
                CategoryDto categoryDto = CategoriesMapper.toCategoryDto(events.getCategory());
                UserShortDto userShortDto = UserMapper.userShortDto(events.getInitiator());
                eventFullDtoList.add(EventsMapper.toEventFullDto(events, categoryDto, userShortDto));
            }
        }
        return ResponseEntity.ok(eventFullDtoList);
    }

    public ResponseEntity<Object> updateEvents(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        System.out.println(updateEventAdminRequest);
        if (eventsRepository.findById(eventId).isEmpty()) {
            throw new NotFoundException("Такой ивент не обнаружен");
        }
        Events events = eventsRepository.findById(eventId).get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (updateEventAdminRequest.getEventDate() != null) {
            if (LocalDateTime.parse(updateEventAdminRequest.getEventDate(), formatter).isBefore(LocalDateTime.now())) {
                throw new BadRequestException("Время ивента прошло");
            }
        }
        if (updateEventAdminRequest.getStateAction() != null) {
            if (updateEventAdminRequest.getStateAction().equals(StateActionAdmin.PUBLISH_EVENT) &&
                    events.getState().equals(State.PUBLISHED)) {
                throw new ConflictException("Попытка публикации опубликованного события");
            }
            if (updateEventAdminRequest.getStateAction().equals(StateActionAdmin.PUBLISH_EVENT) &&
                    events.getState().equals(State.CANCELED)) {
                throw new ConflictException("Попытка публикации отмененного события");
            }
            if (updateEventAdminRequest.getStateAction().equals(StateActionAdmin.REJECT_EVENT) &&
                    events.getState().equals(State.PUBLISHED)) {
                throw new ConflictException("Попытка отмены опубликованного события");
            }
        }
        Events updateEvents = EventsMapper.updateForAdmin(events, updateEventAdminRequest);
        eventsRepository.save(updateEvents);
        CategoryDto categoryDto = CategoriesMapper.toCategoryDto(updateEvents.getCategory());
        UserShortDto userShortDto = UserMapper.userShortDto(updateEvents.getInitiator());
        EventFullDto eventFullDtoDto = EventsMapper.toEventFullDto(eventsRepository.findById(eventId).get(),
                categoryDto, userShortDto);
        return ResponseEntity.ok(eventFullDtoDto);
    }

    public ResponseEntity<Object> getUsers(List<Integer> ids, int from, int size) {
        if (ids == null) {
            return ResponseEntity.ok(userRepository.findAll(PageRequest.of(from > 0 ? from / size : 0, size)).toList());
        }
        return ResponseEntity.ok(userRepository.findById(ids, PageRequest.of(from > 0 ? from / size : 0, size)));
    }

    public ResponseEntity<Object> addNewUser(NewUserDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ConflictException("Такая почта уже используется");
        }
        User user = UserMapper.toUser(dto);
        userRepository.save(user);
        UserDto userDto = UserMapper.toUserDto(userRepository.findByNameAndEmail(user.getName(),
                user.getEmail()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
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
        if (dto.getEvents() != null) {
            for (Integer eventId : eventList) {
                CompilationsEvents compilationsEvents = CompilationsEvents.builder()
                        .eventId(eventId)
                        .compilationId(compilation.getId())
                        .build();
                compilationsEventsList.add(compilationsEvents);
            }
            compilationsEventsRepository.saveAll(compilationsEventsList);
        }
        Compilation compilationTemp = compilationRepository.findByPinnedAndTitle(dto.isPinned(), dto.getTitle());
        List<CompilationsEvents> compilationsEvents = compilationsEventsRepository
                .findByCompilationId(compilationTemp.getId());
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        if (compilationsEvents != null) {
            List<Integer> eventsIdList = new ArrayList<>();
            for (CompilationsEvents events : compilationsEvents) {
                eventsIdList.add(events.getEventId());
            }
            List<Events> eventsList = eventsRepository.findAll(eventsIdList);
            for (Events events : eventsList) {
                EventShortDto shortDto = EventsMapper.toEventShortDto(events);
                eventShortDtoList.add(shortDto);
            }
        }
        CompilationDto compilationDto = CompilationMapper
                .toCompilationDto(compilationTemp, eventShortDtoList);
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationDto);
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
        if (updateCompilationRequest.getEvents() != null) {
            for (Integer eventId : eventList) {
                CompilationsEvents compilationsEvents = CompilationsEvents.builder()
                        .eventId(eventId)
                        .compilationId(compId)
                        .build();
                compilationsEventsRepository.save(compilationsEvents);
            }
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
