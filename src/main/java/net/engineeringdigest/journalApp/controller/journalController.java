package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/journal")
public class journalController {
    @Autowired
    private JournalService service;

    @Autowired
    private GetUsernameByToken getUsernameByToken;
    @GetMapping
    public ResponseEntity<?> getAllJournalsOfUser(){
        return service.getAllJournalsOfUser(getUsernameByToken.getUsernameByAuthenticatedToken());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id){
        return service.getJournalById(getUsernameByToken.getUsernameByAuthenticatedToken(),id);
    }

    @PostMapping
    public ResponseEntity<?> createJournal(@RequestBody JournalEntry myEntry){
        return service.createNewJournal(getUsernameByToken.getUsernameByAuthenticatedToken(),myEntry);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editJournal(@PathVariable ObjectId id, @RequestBody JournalEntry myEntry){
        return service.editJournalById(getUsernameByToken.getUsernameByAuthenticatedToken(),id,myEntry);
    }

    @DeleteMapping("/{id}")
    public void delById(@PathVariable ObjectId id){
        service.delJournalById(getUsernameByToken.getUsernameByAuthenticatedToken(),id);
    }
}