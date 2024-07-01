package exploreWithMe.Dto.model;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Hit {
    private int id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
