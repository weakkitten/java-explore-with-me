package exploreWithMe.Dto.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "")
@Slf4j
@RequiredArgsConstructor
public class DtoController {
    private final DtoController service;

    @PostMapping("/hit")
    public ResponseEntity<Object> saveHit() {
        return service.saveHit();
    }

    @GetMapping("stats")
    public ResponseEntity<Object> getStat(@RequestParam LocalDateTime start,
                                          @RequestParam LocalDateTime end,
                                          @RequestParam List<String> uris,
                                          @RequestParam(defaultValue = "false") boolean unique) {
        return service.getStat(start, end, uris, unique);
    }
}
