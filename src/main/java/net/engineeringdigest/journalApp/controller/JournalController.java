package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.service.journal.JournalService;
import net.engineeringdigest.journalApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalController {
    @Autowired
            private JournalService journalService;
    @Autowired
            private UserService userService;

    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournalsOfUser(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getAllJournalsByUsername(userName);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Journal> getById(@PathVariable String id){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return journalService.getJournalOfUserById(id, userName);
    }

    @PostMapping
    public ResponseEntity<Journal> addNewJournal(@RequestBody Journal journal){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return journalService.addNewJournal(journal, userName);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return journalService.deleteJournalById(id, userName);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<Void> edit(@PathVariable String id, @RequestBody Journal journal){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return journalService.editJournalById(id, journal, userName);
    }
}
