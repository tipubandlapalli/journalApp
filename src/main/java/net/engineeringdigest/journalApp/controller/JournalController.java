package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalController {
    @Autowired
            private JournalService journalService;
    @Autowired
            private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<List<Journal>> getAllJournalsOfUser(@PathVariable String userName){
        return userService.getAllJournalsByUsername(userName);
    }

    @GetMapping("id/{id}/{userName}")
    public ResponseEntity<Journal> getById(@PathVariable ObjectId id,@PathVariable String userName){
        return journalService.getJournalOfUserById(id, userName);
    }

    @PostMapping("{userName}")
    public ResponseEntity<Journal> addNewJournal(@RequestBody Journal journal, @PathVariable String userName){
        return journalService.addNewJournal(journal, userName);
    }

    @DeleteMapping("id/{id}/{userName}")
    public ResponseEntity<Void> deleteById(@PathVariable ObjectId id, @PathVariable String userName){
        return journalService.deleteJournalById(id, userName);
    }

    @PutMapping("id/{id}/{userName}")
    public ResponseEntity<Void> edit(@PathVariable ObjectId id, @RequestBody Journal journal,  @PathVariable String userName){
      return journalService.editJournalById(id, journal, userName);
    }
}
