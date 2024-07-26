package ru.practicum.ewm.event.model;

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
@Table(name = "views", schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
public class Views {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int eventId;
    private String ip;
}
