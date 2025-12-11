package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.service.JournalService;
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


    @GetMapping
    public ResponseEntity<List<Journal>> getAll(){
        return journalService.getAllJournals();
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Journal> getById(@PathVariable ObjectId id){
        return journalService.getJournalById(id);
    }

    @PostMapping
    public ResponseEntity<Journal> addNewJournal(@RequestBody Journal journal){
        return journalService.addNewJournal(journal);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable ObjectId id){
        return journalService.deleteJournalById(id);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<Boolean> edit(@PathVariable ObjectId id, @RequestBody Journal journal){
      return journalService.editJournalById(id, journal);
    }
}
