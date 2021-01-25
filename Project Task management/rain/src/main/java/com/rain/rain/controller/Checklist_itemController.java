package com.rain.rain.controller;

import com.rain.rain.exception.ResourceNotFoundException;
import com.rain.rain.models.Checklist_item;
import com.rain.rain.repo.ChecklistRepo;
import com.rain.rain.repo.Checklist_itemRepo;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rain")
public class Checklist_itemController {
    @Autowired
    private ChecklistRepo checklistRepo;

    @Autowired
    private Checklist_itemRepo checklist_itemRepo;


    @GetMapping("/checklist/{checklistId}/checklist_item")
    public Page<Checklist_item> getAllChecklist_itemByChecklistId(@PathVariable(value = "checklistId") int checklistId,
                                                   Pageable pageable){
        return checklist_itemRepo.findByChecklistId(checklistId, pageable);
    }

    @PostMapping("/checklist/{checklistId}/checklist_item")
    public Checklist_item createChecklist(@PathVariable(value = "checklistId") int checklistId,
                                     @Validated @NotNull @RequestBody Checklist_item checklist_item){
        return checklistRepo.findById(checklistId).map(checklist -> {
            checklist_item.setChecklist(checklist);
            return checklist_itemRepo.save(checklist_item);
        }).orElseThrow(() -> new ResourceNotFoundException("Checklist", "Id", checklistId));
    }

    @PutMapping("/checklist/{checklistId}/checklist_item/{checklist_itemId}")
    public Checklist_item modifyChecklist(@PathVariable(value = "checklistId") int checklistId,
                                     @PathVariable(value = "checklist_itemId") int checklist_itemId,
                                     @Validated @NotNull @RequestBody Checklist_item checklist_itemRequest){
        if (!checklistRepo.existsById(checklistId)) {
            throw new ResourceNotFoundException("Checklist", "Id", checklistId);
        }
        return checklist_itemRepo.findById(checklist_itemId).map(checklist_item -> {
            checklist_item.setName(checklist_itemRequest.getName());
            checklist_item.setStatus(checklist_itemRequest.getStatus());
            return checklist_itemRepo.save(checklist_item);
        }).orElseThrow(() -> new ResourceNotFoundException("Checklist", "Id", checklistId));
    }


    @DeleteMapping("/checklist/{checklistId}/checklist_item/{checklist_itemId}")
    public ResponseEntity<?> deleteChecklist(@PathVariable(value = "checklistId") int checklistId,
                                             @PathVariable(value = "checklist_itemId") int checklist_itemId) {
        if (!checklistRepo.existsById(checklistId)) {
            throw new ResourceNotFoundException("Checklist", "Id", checklistId);
        }

        return checklist_itemRepo.findById(checklist_itemId).map(checklist_item -> {
            checklist_itemRepo.delete(checklist_item);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Checklist_item", "Id", checklist_itemId));
    }

}
