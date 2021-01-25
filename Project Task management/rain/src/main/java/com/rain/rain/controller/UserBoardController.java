package com.rain.rain.controller;

import com.rain.rain.exception.ResourceNotFoundException;
import com.rain.rain.models.User_Board;
import com.rain.rain.repo.BoardRepo;
import com.rain.rain.repo.User_BoardRepo;
import com.rain.rain.repo.UserRepo;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rain")

public class UserBoardController {
    @Autowired
    User_BoardRepo userBoardRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    BoardRepo boardRepo;

    // Get All UserBoard
    @GetMapping("/userboard")
    public List<User_Board> GetAllUserBoard() {
        return userBoardRepo.findAll();
    }

    @GetMapping("/boards/{boardId}/userboard")
    public Page<User_Board> getAllUser_BoardByBoardId(@PathVariable(value = "boardId") int boardId,
                                                                Pageable pageable){
        return userBoardRepo.findByBoardId(boardId, pageable);
    }

    @GetMapping("/users/{userId}/userboard")
    public Page<User_Board> getAllUser_BoardByUserId(@PathVariable(value = "userId") int userId,
                                                                           Pageable pageable){
        return userBoardRepo.findByUserId(userId, pageable);
    }

    // Create a new User_Board
    @PostMapping("/boards/{boardId}/users/{userId}/userboard")
    public User_Board CreateNewUserBoard(@PathVariable(value = "boardId") int boardId,
                                         @PathVariable(value = "userId") int userId,
                                         @Validated @NotNull @RequestBody User_Board userBoard){

        return boardRepo.findById(boardId).map(board -> {
                        userBoard.setBoard(board);
                        return userRepo.findById(userId).map(user -> {
                            userBoard.setUser(user);
                            return userBoardRepo.save(userBoard);
                        }).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", userId));
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", boardId));
        }

    // Update an UserBoard
    @PutMapping("/boards/{boardId}/users/{userId}/userboard/{userBoardId}")
    public User_Board UpdateUserBoard(@PathVariable(value = "boardId") int boardId,
                                      @PathVariable(value = "userId") int userId,
                                      @PathVariable(value = "userBoardId") int userBoardId,
                                      @Validated @NotNull @RequestBody User_Board userBoardRequest) {
        if (!boardRepo.existsById(boardId)) {
            throw new ResourceNotFoundException("Board", "Id", boardId);
        }
        else if (!userRepo.existsById(userId)) {
            throw new ResourceNotFoundException("User", "Id", userId);
        }
        return userBoardRepo.findById(userBoardId).map(user_board -> {
            user_board.setUser_board_rank(userBoardRequest.getUser_board_rank());
            return userBoardRepo.save(user_board);
        }).orElseThrow(() -> new ResourceNotFoundException("User_Board", "Id", userBoardId));
    }


    // Delete a User_Board
    @DeleteMapping("/userboard/{id}")
    public ResponseEntity<?> DeleteUserBoard(@PathVariable(value = "id") int userBoardId) {

        User_Board userBoard = userBoardRepo.findById(userBoardId)
                .orElseThrow(() -> new ResourceNotFoundException("UserBoard", "id", userBoardId));

        userBoardRepo.delete(userBoard);

        return ResponseEntity.ok().build();
    }




}
