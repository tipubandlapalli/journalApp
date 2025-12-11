package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Journal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("journal")
public class JournalController {

    Map<String, Journal> map = new HashMap<>();

    @GetMapping
    public List<Journal> getAll(){
        return new ArrayList<>(map.values());
    }

    @GetMapping("id/{id}")
    public Journal getById(@PathVariable String id){
        return map.get(id);
    }

    @PostMapping
    public boolean addNewJournal(@RequestBody Journal journal){
        if(map.get(journal.getId()) != null) {
            return false;
        }
        map.put(journal.getId(), journal);
        return true;
    }

    @DeleteMapping("id/{id}")
    public boolean deleteById(@PathVariable String id){
        if(map.get(id) == null) {
            return false;
        }
        map.remove(id);
        return true;
    }

    @PutMapping("id/{id}")
    public Journal edit(@PathVariable String id, @RequestBody Journal journal){
        Journal existing = map.remove(id);
        if(journal.getId() != null && !journal.getId().isEmpty()) existing.setId(journal.getId());
        if(journal.getTitle() != null && !journal.getTitle().isEmpty()) existing.setTitle(journal.getTitle());
        if(journal.getContent() != null && !journal.getContent().isEmpty()) existing.setContent(journal.getContent());
        return map.put(existing.getId(), existing );
    }
}
