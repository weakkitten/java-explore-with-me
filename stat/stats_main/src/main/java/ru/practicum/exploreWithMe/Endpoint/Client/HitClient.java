package ru.practicum.exploreWithMe.Endpoint.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.exploreWithMe.Endpoint.model.Hit;
import ru.practicum.exploreWithMe.client.BaseClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class HitClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public HitClient(@Value("${explore-with-me-server.url}") String serverUrl, RestTemplateBuilder builder) {
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

    public ResponseEntity<Object> getStat(String start,
                                          String end,
                                          List<String> uris,
                                          boolean unique) {
        if (uris == null) {
            Map<String, Object> parameters = Map.of(
                    "start", start,
                    "end", end,
                    "unique", unique
            );
            return get("/stats?start={start}&end={end}&unique={unique}", parameters);
        } else {
            Map<String, Object> parameters = Map.of(
                    "start", start,
                    "end", end,
                    "uri", uris,
                    "unique", unique
            );
            return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        }
    }
}
