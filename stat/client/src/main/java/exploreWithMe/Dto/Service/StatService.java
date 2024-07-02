package exploreWithMe.Dto.Service;

import exploreWithMe.Dto.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatService {
    private final StatRepository repository;


}
