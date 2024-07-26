package ru.practicum.ewm.comments.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDtoRequest {
    private int id;
    private int userId;
    private int eventId;
    private String times;
    private String text;
}
