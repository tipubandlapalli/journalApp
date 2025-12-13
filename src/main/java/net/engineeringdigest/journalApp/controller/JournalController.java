package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.securitycontext.AuthenticatedUserUtility;
import net.engineeringdigest.journalApp.service.journal.JournalService;
import net.engineeringdigest.journalApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("journal")
public class JournalController {
    @Autowired
            private JournalService journalService;
    @Autowired
            private UserService userService;
    @Autowired
            private AuthenticatedUserUtility authenticatedUserUtility;

    @GetMapping
    public ResponseEntity<?> getAllJournalsOfUser(){
        UserEntity userEntity = authenticatedUserUtility.getAuthenticatedUserEntity();
        return new ResponseEntity<>(userEntity.getJournals(), HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Journal> getById(@PathVariable String id){
        UserEntity userEntity = authenticatedUserUtility.getAuthenticatedUserEntity();
        Journal journal = journalService.getJournalOfUserById(id, userEntity);
        if(journal != null) {
            return new ResponseEntity<>(journal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewJournal(@RequestBody Journal journal){
        if(journal.getId() != null){
            return new ResponseEntity<>("id should be not be sent", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = authenticatedUserUtility.getAuthenticatedUserEntity();
        return new ResponseEntity<>(journalService.addNewJournal(journal, userEntity), HttpStatus.CREATED);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        UserEntity userEntity = authenticatedUserUtility.getAuthenticatedUserEntity();
        if( journalService.deleteJournalById(id, userEntity)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<Void> edit(@PathVariable String id, @RequestBody Journal journal){

        UserEntity userEntity = authenticatedUserUtility.getAuthenticatedUserEntity();

        if( journalService.editJournalById(id, journal, userEntity)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
