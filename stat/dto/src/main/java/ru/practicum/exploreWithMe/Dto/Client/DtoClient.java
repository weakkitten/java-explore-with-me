package ru.practicum.exploreWithMe.Dto.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.exploreWithMe.client.BaseClient;
import ru.practicum.exploreWithMe.Dto.model.Hit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class DtoClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public DtoClient(@Value("${explore-with-me-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> saveHit(Hit hit) {
        return post("/hit" , hit);
    }

    public ResponseEntity<Object> getStat(LocalDateTime start,
                                          LocalDateTime end,
                                          List<String> uris,
                                          boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uri", uris,
                "unique", unique
        );
        ResponseEntity<Object> viewStats = get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        return viewStats;
    }
}
