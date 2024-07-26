package ru.practicum.ewm.compilation.model;

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
@Table(name = "compilation", schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean pinned;
    private String title;
}
