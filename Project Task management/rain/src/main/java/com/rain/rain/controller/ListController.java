package com.rain.rain.controller;


import com.rain.rain.exception.ResourceNotFoundException;
import com.rain.rain.models.List;
import com.rain.rain.repo.BoardRepo;
import com.rain.rain.repo.ListRepo;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rain")
public class ListController {

    @Autowired
    private ListRepo listrepo;

    @Autowired
    private BoardRepo boardrepo;

    @GetMapping("/boards/{boardId}/lists")
    public Page<List> getAllListsByBoardId(@PathVariable(value = "boardId") int boardId,
                                           Pageable pageable){
        return listrepo.findByBoardId(boardId, pageable);
    }

    @PostMapping("/boards/{boardId}/lists")
    public List createList(@PathVariable(value = "boardId") int boardId,
                           @Validated @NotNull @RequestBody List list){
        return boardrepo.findById(boardId).map(board -> {
            list.setBoard(board);
            return listrepo.save(list);
        }).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", boardId));
    }

    @PutMapping("/boards/{boardId}/lists/{listId}")
    public List modifyList(@PathVariable(value = "boardId") int boardId,
                           @PathVariable(value = "listId") int listId,
                           @Validated @NotNull @RequestBody List listRequest){
        if (!boardrepo.existsById(boardId)) {
            throw new ResourceNotFoundException("Board", "Id", boardId);
        }

        return listrepo.findById(listId).map(list -> {
            list.setList_name(listRequest.getList_name());
            list.setList_description(listRequest.getList_description());
            list.setColor(listRequest.getColor());
            return listrepo.save(list);
        }).orElseThrow(() -> new ResourceNotFoundException("list", "Id", listId));
    }


    @DeleteMapping("/boards/{boardId}/lists/{listId}")
    public ResponseEntity<?> deleteList(@PathVariable(value = "boardId") int boardId,
                                           @PathVariable(value = "listId") int listId) {
        if (!boardrepo.existsById(boardId)) {
            throw new ResourceNotFoundException("Board", "Id", boardId);
        }

        return listrepo.findById(listId).map(list -> {
            listrepo.delete(list);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("List", "Id", listId));
    }
}

