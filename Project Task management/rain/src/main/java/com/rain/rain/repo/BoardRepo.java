package com.rain.rain.repo;


import com.rain.rain.models.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepo extends JpaRepository<Board, Integer> {
//    static List<Board> findByBoard_id(Integer board_id);
}
