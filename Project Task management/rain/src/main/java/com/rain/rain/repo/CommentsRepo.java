package com.rain.rain.repo;

import com.rain.rain.models.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentsRepo extends JpaRepository<Comments, Integer> {
    Page<Comments> findByCardId(int cardId, Pageable pageable);
    Page<Comments> findByUserId(int userId, Pageable pageable);
}
