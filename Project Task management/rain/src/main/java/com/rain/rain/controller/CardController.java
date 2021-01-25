package com.rain.rain.controller;

import com.rain.rain.exception.ResourceNotFoundException;
import com.rain.rain.models.Card;
import com.rain.rain.repo.CardRepo;
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
public class CardController {

    @Autowired
    private CardRepo cardrepo;

    @Autowired
    private ListRepo listrepo;

    @GetMapping("/lists/{listId}/cards")
    public Page<Card> getAllCardsByListId(@PathVariable(value = "listId") int listId,
                                          Pageable pageable){
        return cardrepo.findByListId(listId, pageable);
    }

    @PostMapping("/lists/{listId}/cards")
    public Card createCard(@PathVariable(value = "listId") int listId,
                           @Validated @NotNull @RequestBody Card card){
        return listrepo.findById(listId).map(list -> {
            card.setList(list);
            return cardrepo.save(card);
        }).orElseThrow(() -> new ResourceNotFoundException("List", "Id", listId));
    }

    @PutMapping("/lists/{listId}/cards/{cardId}")
    public Card ModifyCard(@PathVariable(value = "listId") int listId,
                           @PathVariable(value = "cardId") int cardId,
                           @Validated @NotNull @RequestBody Card cardRequest){
        if (!listrepo.existsById(listId)) {
            throw new ResourceNotFoundException("List", "Id", listId);
        }

        return cardrepo.findById(cardId).map(card -> {
            card.setName(cardRequest.getName());
            card.setDescription(cardRequest.getDescription());
            card.setCard_Rank(cardRequest.getCard_Rank());
            card.setCard_Type(cardRequest.getCard_Type());
            card.setMusic(cardRequest.getMusic());
            return cardrepo.save(card);
        }).orElseThrow(() -> new ResourceNotFoundException("list", "Id", listId));
    }


    @DeleteMapping("/lists/{listId}/cards/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable(value = "listId") int listId,
                                        @PathVariable(value = "cardId") int cardId) {
        if (!listrepo.existsById(listId)) {
            throw new ResourceNotFoundException("List", "Id", listId);
        }

        return cardrepo.findById(cardId).map(card -> {
            cardrepo.delete(card);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Card", "Id", cardId));
    }
}

