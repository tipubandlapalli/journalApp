package net.engineeringdigest.journalapp.controller;

import net.engineeringdigest.journalapp.dto.JournalRequest;
import net.engineeringdigest.journalapp.entity.Journal;
import net.engineeringdigest.journalapp.entity.UserEntity;
import net.engineeringdigest.journalapp.securitycontext.AuthenticatedUserUtility;
import net.engineeringdigest.journalapp.service.journal.JournalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalController {

    private final JournalService journalService;
    private final AuthenticatedUserUtility authenticatedUserUtility;

    public JournalController(JournalService journalService, AuthenticatedUserUtility authenticatedUserUtility) {
        this.journalService = journalService;
        this.authenticatedUserUtility = authenticatedUserUtility;
    }

    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournalsOfUser(){
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
    public ResponseEntity<Journal> addNewJournal(@RequestBody JournalRequest journalRequest){
        Journal journal = journalRequest.convert();
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
    public ResponseEntity<Void> edit(@PathVariable String id, @RequestBody JournalRequest journalRequest){
        UserEntity userEntity = authenticatedUserUtility.getAuthenticatedUserEntity();
        Journal journal = journalRequest.convert();

        if( journalService.editJournalById(id, journal , userEntity)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
