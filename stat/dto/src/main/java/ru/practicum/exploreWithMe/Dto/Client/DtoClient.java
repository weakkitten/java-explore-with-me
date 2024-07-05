package ru.practicum.exploreWithMe.Dto.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.exploreWithMe.Dto.model.dto.DtoMapper;
import ru.practicum.exploreWithMe.Dto.model.dto.HitDto;
import ru.practicum.exploreWithMe.client.BaseClient;
import ru.practicum.exploreWithMe.Dto.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class DtoClient extends BaseClient {

    @Autowired
    public DtoClient(@Value("${explore-with-me-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> saveHit(HitDto hitDto) {
        Hit hit =  DtoMapper.toHit(hitDto);
        return post("/hit" , hit);
    }

    public ResponseEntity<Object> getStat(String start,
                                          String end,
                                          List<String> uris,
                                          boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "unique", unique
        );
        if (uris == null) {
            return get("/stats?start={start}&end={end}&unique={unique}", parameters);
        } else {
            System.out.println("Список - " + uris.get(0));
            return get("/stats?start={start}&end={end}&uris=" + uris.get(0) + "&unique={unique}", parameters);
        }
    }
}
