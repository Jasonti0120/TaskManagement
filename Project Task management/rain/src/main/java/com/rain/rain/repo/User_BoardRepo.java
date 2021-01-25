package com.rain.rain.repo;


import com.rain.rain.models.User_Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface User_BoardRepo extends JpaRepository<User_Board, Integer> {
    Page<User_Board> findByUserId(Integer UserId, Pageable pageable);
    Page<User_Board> findByBoardId(Integer BoardId, Pageable pageable);

}
