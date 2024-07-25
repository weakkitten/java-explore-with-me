package ru.practicum.ewm.Service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.Dto.model.ViewStatsInterface;
import ru.practicum.ewm.Dto.model.dto.NewHit;
import java.util.List;

@Service
public class StatClientService {
    private final RestTemplate template = new RestTemplate();

    public void saveHit(NewHit hit) {
        template.postForEntity("http://stats-server:9090/hit",
                hit,
                Void.class);
    }

    public Object getStat(String start,
                          String end,
                          List<String> uris,
                          boolean unique) {
        return template.exchange("http://stats-server:9090/stats?start=" + start + "&end=" + end +
                "&uris=" + uris + "&unique=" + unique,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ViewStatsInterface>>() {}).getBody();
    }
}
