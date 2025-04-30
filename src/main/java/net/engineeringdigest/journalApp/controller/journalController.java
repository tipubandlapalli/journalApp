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

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalsOfUser(@PathVariable String username){
        return service.getAllJournalsOfUser(username);
    }

    @GetMapping("/{username}/{id}")
    public ResponseEntity<?> getById(@PathVariable String username,@PathVariable ObjectId id){
        return service.getJournalById(username,id);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> createJournal(@RequestBody JournalEntry myEntry,@PathVariable String username){
        return service.createNewJournal(username,myEntry);
    }

    @PutMapping("/{username}/{id}")
    public ResponseEntity<?> editJournal(@PathVariable String username,@PathVariable ObjectId id, @RequestBody JournalEntry myEntry){
        return service.editJournalById(username,id,myEntry);
    }

    @DeleteMapping("/{username}/{id}")
    public void delById(@PathVariable String username,@PathVariable ObjectId id){
        service.delJournalById(username,id);
    }
}