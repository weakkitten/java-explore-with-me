package ru.practicum.ewm.event.model;

import lombok.*;
import ru.practicum.ewm.categories.model.Categories;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.utility.State;

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
@Table(name = "events", schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String annotation;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "confirmed_requests")
    private int confirmedRequests;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @Column(name = "initiator_id")
    private Integer initiatorId;
    private Float lat;
    private Float lon;
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state;
    private String title;
    private int views;
    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Categories category;
    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private User initiator;
}
