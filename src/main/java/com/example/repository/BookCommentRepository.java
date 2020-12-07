package com.example.repository;

import com.example.model.BookComment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
    List<BookComment> findByComment (String comment);
}
