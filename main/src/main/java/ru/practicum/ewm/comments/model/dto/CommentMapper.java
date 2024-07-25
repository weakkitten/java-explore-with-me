package ru.practicum.ewm.comments.model.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.comments.model.Comments;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class CommentMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Comments toComment(NewCommentDto dto, int userId) {
        return Comments.builder()
                .text(dto.getText())
                .times(LocalDateTime.now())
                .userId(userId)
                .eventId(dto.getEventId())
                .build();
    }

    public static CommentDtoRequest toCommentRequest(Comments comments) {
            return CommentDtoRequest.builder()
                    .text(comments.getText())
                    .times(formatter.format(comments.getTimes()))
                    .userId(comments.getUserId())
                    .eventId(comments.getEventId())
                    .build();
    }
}
