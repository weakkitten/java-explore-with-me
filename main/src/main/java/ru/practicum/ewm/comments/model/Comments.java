package ru.practicum.ewm.comments.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Table(name = "users", schema = "public",
        uniqueConstraints = {@UniqueConstraint(columnNames = "id")
})
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int eventId;
    private LocalDateTime times;
    @Column(columnDefinition = "TEXT")
    private String text;
}
