package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repo.JournalRepo;
import net.engineeringdigest.journalApp.repo.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalService {
    @Autowired
    private JournalRepo jRepo;
    @Autowired
    private UserRepo userRepo;
    public ResponseEntity<?> getAllJournalsOfUser(String username)
    {
        User user = userRepo.findByUsername(username);
        if(user != null){
            return new ResponseEntity<>(user.getJouEntries(),HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getJournalById(String username, ObjectId id) {
        User user = userRepo.findByUsername(username);
        if(user != null){
            JournalEntry entry = user.getJouEntries().stream().filter(journalEntry -> journalEntry.getId().equals(id)).findFirst().orElse(null);
            if(entry != null) return new ResponseEntity<>(entry,HttpStatus.OK);
            return new ResponseEntity<>("journal not found in user data",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("user not found",HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<?> createNewJournal(String username,JournalEntry myEntry) {
        User user = userRepo.findByUsername(username);
        if(user != null){
            myEntry.setLocalDateTime(LocalDateTime.now());
            JournalEntry saved = jRepo.save(myEntry);
            user.getJouEntries().add(saved);
            userRepo.save(user);
            return new ResponseEntity<>(saved,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("user not found",HttpStatus.BAD_REQUEST);
    }
    @Transactional
    public ResponseEntity<?> editJournalById(String username,ObjectId id,JournalEntry entry) {
        User user = userRepo.findByUsername(username);
        if(user != null) {
            JournalEntry oldJou = jRepo.findById(id).orElse(null);
            if (oldJou != null) {
                if(user.getJouEntries().stream().anyMatch(j -> j.getId().equals(id))){
                    if (entry.getTitle() != null && !entry.getTitle().isEmpty()) oldJou.setTitle(entry.getTitle());
                    if (entry.getContext() != null && !entry.getContext().isEmpty()) oldJou.setContext(entry.getTitle());
                    jRepo.save(oldJou);
                    return new ResponseEntity<>(oldJou,HttpStatus.ACCEPTED);
                } else {
                    return new ResponseEntity<>("User not authorized",HttpStatus.BAD_GATEWAY);
                }
            }
            return new ResponseEntity<>("journal not found",HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>("user not found",HttpStatus.BAD_GATEWAY);
    }

    @Transactional
    public ResponseEntity<?> delJournalById(String  username,ObjectId id) {
        User user = userRepo.findByUsername(username);
        if(user != null) {
            JournalEntry journal = jRepo.findById(id).orElse(null);
            if(journal != null){
                if(user.getJouEntries().removeIf(j -> j.getId().equals(id))){
                    userRepo.save(user);
                    jRepo.deleteById(id);
                    return new ResponseEntity<>("journal deleted",HttpStatus.ACCEPTED);
                } else {
                    return new ResponseEntity<>("user not authorized",HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>("journal not found",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("user not found",HttpStatus.BAD_REQUEST);
    }
}
