package ru.practicum.ewm_main.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm_main.categories.model.dto.CategoriesMapper;
import ru.practicum.ewm_main.categories.model.dto.CategoryDto;
import ru.practicum.ewm_main.error.exception.BadRequestException;
import ru.practicum.ewm_main.error.exception.NotFoundException;
import ru.practicum.ewm_main.event.model.Events;
import ru.practicum.ewm_main.event.model.Hit;
import ru.practicum.ewm_main.event.model.Views;
import ru.practicum.ewm_main.event.model.dto.EventFullDto;
import ru.practicum.ewm_main.event.model.dto.EventsMapper;
import ru.practicum.ewm_main.event.repository.EventsRepository;
import ru.practicum.ewm_main.event.repository.ViewsRepository;
import ru.practicum.ewm_main.user.model.dto.UserMapper;
import ru.practicum.ewm_main.user.model.dto.UserShortDto;
import ru.practicum.ewm_main.utility.State;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventsService {
    private final EventsRepository repository;
    private final ViewsRepository viewsRepository;

    public ResponseEntity<Object> getEvents(String text,
                                            List<Integer> categories,
                                            boolean paid,
                                            String rangeStart,
                                            String rangeEnd,
                                            boolean onlyAvailable,
                                            String sort,
                                            int from,
                                            int size,
                                            String ip,
                                            String requestUri) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (sort == null) {
            sort = "VIEWS";
        }
        if (rangeEnd != null) {
            if (LocalDateTime.parse(rangeEnd, formatter).isBefore(LocalDateTime.parse(rangeStart, formatter))) {
                throw new BadRequestException("Некорректный отрезок времени");
            }
        }
        List<Events> eventsList;
        Pageable pageable = PageRequest.of(from / size, size);
        if (rangeEnd == null) {
            LocalDateTime times = LocalDateTime.now();
            if (text == null) {
                if (categories == null) {
                    if (paid) {
                        if (onlyAvailable) {//доступные, платные, без категории, времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsPaidAndAvailable(times, pageable);
                            } else {
                                eventsList = repository.getEventsPaidAndAvailableByDate(times, pageable);
                            }
                        } else {//платные, все, без категории, времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsPaid(times, pageable);
                            } else {
                                eventsList = repository.getEventsPaidByDate(times, pageable);
                            }
                        }
                    } else {
                        if (onlyAvailable) {//беспатные, доступные, без категории, времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsAvailable(times, pageable);
                            } else {
                                eventsList = repository.getEventsAvailableByDate(times, pageable);
                            }
                        } else {//бесплатные, все, без категории, времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsAll(times, pageable);
                            } else {
                                eventsList = repository.getEventsAllByDate(times, pageable);
                            }
                        }
                    }
                } else {
                    if (paid) {
                        if (onlyAvailable) {//доступные, платные, с категориями, без времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsWitchCatAndPaidAndAvailable(categories,
                                        times,
                                        pageable);
                            } else {
                                eventsList = repository.getEventsWitchCatAndPaidAndAvailableByDate(categories,
                                        times,
                                        pageable);
                            }
                        } else {//платные, с категориями, без времени и текста, все
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsPaidAndCat(categories, times, pageable);
                            } else {
                                eventsList = repository.getEventsPaidAndCatByDate(categories, times, pageable);
                            }
                        }
                    } else {
                        if (onlyAvailable) {//бесплатные, доступные, с категорией, без времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsAvailableAndCat(categories, times, pageable);
                            } else {
                                eventsList = repository.getEventsAvailableAndCatByDate(categories, times, pageable);
                            }
                        } else {//бесплатные, все, с категорией, без времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsAllAndCat(categories, times, pageable);
                            } else {
                                eventsList = repository.getEventsAllAndCatByDate(categories, times, pageable);
                            }
                        }
                    }
                }
            } else {//есть текст, нет времени
                if (categories == null) {
                    if (paid) {
                        if (onlyAvailable) {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndAvailableAndPaid(text, times, pageable);
                            } else {
                                eventsList = repository.getEventsTextAndAvailableAndPaidByDate(text, times, pageable);
                            }
                        } else {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndPaid(text, times, pageable);
                            } else {
                                eventsList = repository.getEventsTextAndPaidByDate(text, times, pageable);
                            }
                        }
                    } else {
                        if (onlyAvailable) {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndAvailable(text, times, pageable);
                            } else {
                                eventsList = repository.getEventsTextAndAvailableByDate(text, times, pageable);
                            }
                        } else {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsText(text, times, pageable);
                            } else {
                                eventsList = repository.getEventsTextByDate(text, times, pageable);
                            }
                        }
                    }
                } else {//с текстом, категорией, без времени
                    if (paid) {
                        if (onlyAvailable) {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndCategoriesAndAvailableAndPaid(text,
                                        categories,
                                        times,
                                        pageable);
                            } else {
                                eventsList = repository.getEventsTextAndCategoriesAndAvailableAndPaidByDate(text,
                                        categories,
                                        times,
                                        pageable);
                            }
                        } else {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndCategoriesAndPaid(text,
                                        categories,
                                        times,
                                        pageable);
                            } else {
                                eventsList = repository.getEventsTextAndCategoriesAndPaidByDate(text,
                                        categories,
                                        times,
                                        pageable);
                            }
                        }
                    } else {
                        if (onlyAvailable) {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndCategoriesAndAvailable(text,
                                        categories,
                                        times,
                                        pageable);
                            } else {
                                eventsList = repository.getEventsTextAndCategoriesAndAvailableByDate(text,
                                        categories,
                                        times,
                                        pageable);
                            }
                        } else {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndCategories(text, categories, times, pageable);
                            } else {
                                eventsList = repository.getEventsTextAndCategoriesByDate(text, categories,
                                                                                         times, pageable);
                            }
                        }
                    }
                }
            }
        } else {//время есть
            LocalDateTime start = LocalDateTime.parse(rangeStart, formatter);
            LocalDateTime end = LocalDateTime.parse(rangeEnd, formatter);
            if (text == null) {
                if (categories == null) {
                    if (paid) {
                        if (onlyAvailable) {//доступные, платные, без категории, времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsPaidAndAvailable(start, end, pageable);
                            } else {
                                eventsList = repository.getEventsPaidAndAvailableByDate(start, end, pageable);
                            }
                        } else {//платные, все, без категории, времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsPaid(start, end, pageable);
                            } else {
                                eventsList = repository.getEventsPaidByDate(start, end, pageable);
                            }
                        }
                    } else {
                        if (onlyAvailable) {//беспатные, доступные, без категории, времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsAvailable(start, end, pageable);
                            } else {
                                eventsList = repository.getEventsAvailableByDate(start, end, pageable);
                            }
                        } else {//бесплатные, все, без категории, времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsAll(start, end, pageable);
                            } else {
                                eventsList = repository.getEventsAllByDate(start, end, pageable);
                            }
                        }
                    }
                } else {
                    if (paid) {
                        if (onlyAvailable) {//доступные, платные, с категориями, без времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsWitchCatAndPaidAndAvailable(categories, start,
                                        end, pageable);
                            } else {
                                eventsList = repository.getEventsWitchCatAndPaidAndAvailableByDate(categories,
                                        start,
                                        end,
                                        pageable);
                            }
                        } else {//платные, с категориями, без времени и текста, все
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsPaidAndCat(categories, start, end, pageable);
                            } else {
                                eventsList = repository.getEventsPaidAndCatByDate(categories, start, end, pageable);
                            }
                        }
                    } else {
                        if (onlyAvailable) {//бесплатные, доступные, с категорией, без времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsAvailableAndCat(categories, start, end, pageable);
                            } else {
                                eventsList = repository.getEventsAvailableAndCatByDate(categories,start, end, pageable);
                            }
                        } else {//бесплатные, все, с категорей, без времени и текста
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsAllAndCat(categories, start, end, pageable);
                            } else {
                                eventsList = repository.getEventsAllAndCatByDate(categories, start, end, pageable);
                            }
                        }
                    }
                }
            } else {//есть текст, нет времени
                if (categories == null) {
                    if (paid) {
                        if (onlyAvailable) {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndAvailableAndPaid(text.toLowerCase(),
                                        start, end, pageable);
                            } else {
                                eventsList = repository.getEventsTextAndAvailableAndPaidByDate(text.toLowerCase(),
                                        start, end, pageable);
                            }
                        } else {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndPaid(text.toLowerCase(), start, end, pageable);
                            } else {
                                eventsList = repository.getEventsTextAndPaidByDate(text.toLowerCase(),
                                        start, end, pageable);
                            }
                        }
                    } else {
                        if (onlyAvailable) {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndAvailable(text.toLowerCase(),
                                        start, end, pageable);
                            } else {
                                eventsList = repository.getEventsTextAndAvailableByDate(text.toLowerCase(),
                                        start, end, pageable);
                            }
                        } else {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsText(text.toLowerCase(), start, end, pageable);
                            } else {
                                eventsList = repository.getEventsTextByDate(text.toLowerCase(), start, end, pageable);
                            }
                        }
                    }
                } else {//с текстом, категорией, без времени
                    if (paid) {
                        if (onlyAvailable) {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndCategoriesAndAvailableAndPaid(text.toLowerCase(),
                                        categories,
                                        start,
                                        end,
                                        pageable);
                            } else {
                                eventsList = repository
                                        .getEventsTextAndCategoriesAndAvailableAndPaidByDate(text.toLowerCase(),
                                        categories,
                                        start,
                                        end,
                                        pageable);
                            }
                        } else {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndCategoriesAndPaid(text.toLowerCase(),
                                        categories,
                                        start,
                                        end,
                                        pageable);
                            } else {
                                System.out.println("Я попадаю сюда?");
                                System.out.println("Текст - " + text.toLowerCase());
                                System.out.println(categories);
                                System.out.println(start);
                                System.out.println(end);
                                eventsList = repository.getEventsTextAndCategoriesAndPaidByDate(text.toLowerCase(),
                                        categories,
                                        start,
                                        end,
                                        pageable);
                            }
                        }
                    } else {
                        if (onlyAvailable) {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndCategoriesAndAvailable(text.toLowerCase(),
                                        categories,
                                        start,
                                        end,
                                        pageable);
                            } else {
                                eventsList = repository.getEventsTextAndCategoriesAndAvailableByDate(text.toLowerCase(),
                                        categories,
                                        start,
                                        end,
                                        pageable);
                            }
                        } else {
                            if (sort.equals("VIEWS")) {
                                eventsList = repository.getEventsTextAndCategories(text.toLowerCase(),
                                        categories, start, end, pageable);
                            } else {
                                eventsList = repository.getEventsTextAndCategoriesByDate(text.toLowerCase(), categories,
                                        start, end, pageable);
                            }
                        }
                    }
                }
            }
        }
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (Events events : eventsList) {
            CategoryDto categoryDto = CategoriesMapper.toCategoryDto(events.getCategory());
            UserShortDto userDto = UserMapper.userShortDto(events.getInitiator());
            EventFullDto eventFullDto = EventsMapper.toEventFullDto(events, categoryDto, userDto);
            eventFullDtoList.add(eventFullDto);
        }
        RestTemplate template = new RestTemplate();
        Hit hit = Hit.builder()
                .app("ewm-main")
                .uri(requestUri)
                .ip(ip)
                .timestamp(LocalDateTime.now())
                .build();
        template.postForEntity("http://stats-server:9090/hit", hit, Void.class);
        return ResponseEntity.ok(eventFullDtoList);
    }

    public ResponseEntity<Object> getEventsById(int id, String ip, String requestUri) {
        if (repository.findById(id).get().getState() != State.PUBLISHED) {
            throw new NotFoundException("Не найдено");
        }
        if (viewsRepository.findByEventIdAndIp(id, ip).isEmpty()) {
            Events events = repository.findById(id).get();
            events.setViews(events.getViews() + 1);
            repository.save(events);
            Views views = Views.builder()
                    .eventId(id)
                    .ip(ip)
                    .build();
            viewsRepository.save(views);
        }
        RestTemplate template = new RestTemplate();
        Hit hit = Hit.builder()
                .app("ewm-main")
                .uri(requestUri)
                .ip(ip)
                .timestamp(LocalDateTime.now())
                .build();
        template.postForEntity("http://stats-server:9090/hit", hit, Void.class);
        Events events = repository.findById(id).get();
        CategoryDto categoryDto = CategoriesMapper.toCategoryDto(events.getCategory());
        UserShortDto userShortDto = UserMapper.userShortDto(events.getInitiator());
        EventFullDto fullDto = EventsMapper.toEventFullDto(events, categoryDto, userShortDto);
        return ResponseEntity.status(HttpStatus.OK).body(fullDto);
    }
}
