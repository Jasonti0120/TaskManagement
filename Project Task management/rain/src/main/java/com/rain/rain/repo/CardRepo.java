package com.rain.rain.repo;

import com.rain.rain.models.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends JpaRepository<Card, Integer> {
    Page<Card> findByListId(int listId, Pageable pageable);
}
