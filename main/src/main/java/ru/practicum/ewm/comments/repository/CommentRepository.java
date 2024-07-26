package ru.practicum.ewm.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.comments.model.Comments;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Integer> {
    Comments findByUserIdAndEventIdAndText(int userId, int eventId, String text);
    
    List<Comments> findByEventId(int eventId);
}
