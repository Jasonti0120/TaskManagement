package com.rain.rain.controller;

import com.rain.rain.exception.ResourceNotFoundException;
import com.rain.rain.models.Checklist;
import com.rain.rain.repo.CardRepo;
import com.rain.rain.repo.ChecklistRepo;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rain")
public class ChecklistController {
    @Autowired
    private ChecklistRepo checklistRepo;

    @Autowired
    private CardRepo cardrepo;


    @GetMapping("/cards/{cardId}/checklist")
    public Page<Checklist> getAllChecklistByCardId(@PathVariable(value = "cardId") int cardId,
                                                   Pageable pageable){
        return checklistRepo.findByCardId(cardId, pageable);
    }

    @PostMapping("/cards/{cardId}/checklist")
    public Checklist createChecklist(@PathVariable(value = "cardId") int cardId,
                           @Validated @NotNull @RequestBody Checklist checklist){
        return cardrepo.findById(cardId).map(card -> {
                checklist.setCard(card);
                return checklistRepo.save(checklist);
        }).orElseThrow(() -> new ResourceNotFoundException("Card", "Id", cardId));
    }

    @PutMapping("/cards/{cardId}/checklist/{checklistId}")
    public Checklist modifyChecklist(@PathVariable(value = "cardId") int cardId,
                               @PathVariable(value = "checklistId") int checklistId,
                               @Validated @NotNull @RequestBody Checklist checklistRequest){
        if (!cardrepo.existsById(cardId)) {
            throw new ResourceNotFoundException("Card", "Id", cardId);
        }
        return checklistRepo.findById(checklistId).map(checklist -> {
            checklist.setName(checklistRequest.getName());
            return checklistRepo.save(checklist);
        }).orElseThrow(() -> new ResourceNotFoundException("Card", "Id", cardId));
    }


    @DeleteMapping("/cards/{cardId}/checklist/{checklistId}")
    public ResponseEntity<?> deleteChecklist(@PathVariable(value = "cardId") int cardId,
                                            @PathVariable(value = "checklistId") int checklistId) {
        if (!cardrepo.existsById(cardId)) {
            throw new ResourceNotFoundException("Card", "Id", cardId);
        }

        return checklistRepo.findById(checklistId).map(checklist -> {
            checklistRepo.delete(checklist);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Checklist", "Id", checklistId));
    }
}

