package exploreWithMe.Dto.repository;

import exploreWithMe.Dto.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<Integer, Hit> {
}
