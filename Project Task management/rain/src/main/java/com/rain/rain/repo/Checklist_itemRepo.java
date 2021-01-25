package com.rain.rain.repo;

import com.rain.rain.models.Checklist_item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Checklist_itemRepo extends JpaRepository<Checklist_item, Integer> {

    Page<Checklist_item> findByChecklistId(Integer ChecklistId, Pageable pageable);
}
