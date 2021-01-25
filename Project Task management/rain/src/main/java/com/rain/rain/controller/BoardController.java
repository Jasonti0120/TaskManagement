package com.rain.rain.controller;


import com.rain.rain.exception.ResourceNotFoundException;
import com.rain.rain.models.Board;
import com.rain.rain.repo.BoardRepo;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rain")
public class BoardController {

    @Autowired
    private BoardRepo boardrepo;

    //Get all boards
    @GetMapping("boards")
    public List<Board>  getBoards(){
        return boardrepo.findAll();
    }

    // Get a Single Board
    @GetMapping("/boards/{id}")
    public Board getBoardById(@PathVariable(value = "id") int boardId) {
        return boardrepo.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardId));
    }

    //create a board
    @PostMapping("/boards")
    public Board CreateBoard(@Validated @NotNull @RequestBody Board board){
        return boardrepo.save(board);
    }

    //Update a board
    @PutMapping("/boards/{boardId}")
    public Board updateBoard(@PathVariable int boardId, @Validated @NotNull @RequestBody Board boardRequest){
        return boardrepo.findById(boardId).map(board -> {
            board.setBoard_name(boardRequest.getBoard_name());
            board.setBoard_description(boardRequest.getBoard_description());
            board.setColor(boardRequest.getColor());
            return boardrepo.save(board);
        }).orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardId));
    }

    //Delete a board
    @DeleteMapping(value = "/boards/{boardId}")
    public void DeleteBoard(@PathVariable int boardId){
        boardrepo.deleteById(boardId);
    }

}
