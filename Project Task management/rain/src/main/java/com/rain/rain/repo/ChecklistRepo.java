package com.rain.rain.repo;


import com.rain.rain.models.Checklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepo extends JpaRepository<Checklist, Integer> {

    Page<Checklist> findByCardId(Integer CardId, Pageable pageable);
}
