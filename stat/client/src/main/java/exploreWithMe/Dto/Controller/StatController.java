package exploreWithMe.Dto.Controller;

import exploreWithMe.Dto.Service.StatService;
import exploreWithMe.Dto.model.Hit;
import exploreWithMe.Dto.model.ViewStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "")
@Slf4j
@RequiredArgsConstructor
public class StatController {
    private final StatService service;

    @PostMapping("/hit")
    public void saveHit(@RequestBody Hit hit) {
        service.saveHit(hit);
    }

    @GetMapping("stats")
    public List<ViewStats> getStat(@RequestParam LocalDateTime start,
                                   @RequestParam LocalDateTime end,
                                   @RequestParam List<String> uris,
                                   @RequestParam(defaultValue = "false") boolean unique) {
        return service.getStat(start, end, uris, unique);
    }
}
