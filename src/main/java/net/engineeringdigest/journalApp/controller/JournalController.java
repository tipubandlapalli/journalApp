package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalController {
    @Autowired
            private JournalService journalService;


    @GetMapping
    public List<Journal> getAll(){
        return journalService.getAllJournals();
    }

    @GetMapping("id/{id}")
    public Journal getById(@PathVariable ObjectId id){
        return journalService.getJournalById(id);
    }

    @PostMapping
    public boolean addNewJournal(@RequestBody Journal journal){
        return journalService.addNewJournal(journal);
    }

    @DeleteMapping("id/{id}")
    public boolean deleteById(@PathVariable ObjectId id){
        return journalService.deleteJournalById(id);
    }

    @PutMapping("id/{id}")
    public boolean edit(@PathVariable ObjectId id, @RequestBody Journal journal){
      return journalService.editJournalById(id, journal);
    }
}
