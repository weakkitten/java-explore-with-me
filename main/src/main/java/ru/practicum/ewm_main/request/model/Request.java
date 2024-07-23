package ru.practicum.ewm_main.request.model;

import lombok.*;
import ru.practicum.ewm_main.utility.Status;

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
@Table(name = "request", schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int requester;
    private LocalDateTime created;
    @Enumerated(EnumType.STRING)
    private Status status;
    private int event;
}
