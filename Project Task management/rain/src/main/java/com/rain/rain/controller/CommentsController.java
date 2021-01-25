package com.rain.rain.controller;

import com.rain.rain.exception.ResourceNotFoundException;
import com.rain.rain.models.Comments;
import com.rain.rain.repo.CardRepo;
import com.rain.rain.repo.CommentsRepo;
import com.rain.rain.repo.UserRepo;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rain")

public class CommentsController {


    @Autowired
    private CommentsRepo commentrepo;

    @Autowired
    private CardRepo cardrepo;

    @Autowired
    private UserRepo userrepo;

    @GetMapping("/cards/{cardId}/comments")
    public Page<Comments> getAllCommentsByCardId(@PathVariable(value = "cardId") int cardId,
                                                 Pageable pageable){
        return commentrepo.findByCardId(cardId, pageable);
    }


    @PostMapping("/cards/{cardId}/user/{userId}/comments")
    public Comments createComment(@PathVariable(value = "cardId") int cardId,
                                  @PathVariable(value = "userId") int userId,
                           @Validated @NotNull @RequestBody Comments comments){
        return cardrepo.findById(cardId).map(card -> {
            comments.setCard(card);
            return userrepo.findById(userId).map(user -> {
                comments.setUser(user);
                return commentrepo.save(comments);
            }).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        }).orElseThrow(() -> new ResourceNotFoundException("Card", "Id", cardId));
    }

    @PutMapping("/cards/{cardId}/users/{userId}/comments/{commentId}")
    public Comments modifyComment(@PathVariable(value = "cardId") int cardId,
                                  @PathVariable(value = "userId") int userId,
                           @PathVariable(value = "commentId") int commentId,
                           @Validated @NotNull @RequestBody Comments commentRequest){
        if (!cardrepo.existsById(cardId)) {
            throw new ResourceNotFoundException("Card", "Id", cardId);
        }
        if (!userrepo.existsById(userId)) {
            throw new ResourceNotFoundException("User", "Id", userId);
        }

        return commentrepo.findById(commentId).map(comments -> {
            comments.setComment(commentRequest.getComment());
            return commentrepo.save(comments);
        }).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
    }


    @DeleteMapping("/cards/{cardId}/comments/{commentId}")
    public ResponseEntity<?> deleteComments(@PathVariable(value = "cardId") int cardId,
                                        @PathVariable(value = "commentId") int commentId) {
        if (!cardrepo.existsById(cardId)) {
            throw new ResourceNotFoundException("Card", "Id", cardId);
        }

        return commentrepo.findById(commentId).map(comments -> {
            commentrepo.delete(comments);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
    }
}


