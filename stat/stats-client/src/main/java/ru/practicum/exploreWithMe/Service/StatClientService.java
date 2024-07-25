package ru.practicum.exploreWithMe.Service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.Dto.model.ViewStatsInterface;
import ru.practicum.exploreWithMe.Dto.model.dto.NewHit;

import java.util.List;

@Service
public class StatClientService {
    private final RestTemplate template = new RestTemplate();

    public void saveHit(NewHit hit) {
        template.postForEntity("http://stats-server:9090/save",
                hit,
                Void.class);
    }

    public Object getStat(String start,
                          String end,
                          List<String> uris,
                          boolean unique) {
        return template.exchange("http://stats-server:9090/get",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ViewStatsInterface>>() {}).getBody();
    }
}
