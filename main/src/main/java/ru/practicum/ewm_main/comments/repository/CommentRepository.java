package ru.practicum.ewm_main.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.comments.model.Comments;

public interface CommentRepository extends JpaRepository<Comments, Integer> {
    Comments findByUserIdAndEventIdAndText(int userId, int eventId, String text);
}
