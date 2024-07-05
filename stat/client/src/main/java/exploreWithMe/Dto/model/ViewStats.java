package exploreWithMe.Dto.model;

import lombok.*;

@Data
@AllArgsConstructor
public class ViewStats {
    private String app;
    private String uri;
    private Integer hits;
}
