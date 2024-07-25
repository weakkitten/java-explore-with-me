package ru.practicum.ewm_main.event.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Table(name = "compilation_event", schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
public class CompilationsEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int compilationId;
    private int eventId;
}
