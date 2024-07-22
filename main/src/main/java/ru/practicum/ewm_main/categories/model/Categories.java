package ru.practicum.ewm_main.categories.model;

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
@Table(name = "categories", schema = "public",
        uniqueConstraints = {@UniqueConstraint(columnNames = "id"),
                @UniqueConstraint(columnNames = "name")
        })
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
