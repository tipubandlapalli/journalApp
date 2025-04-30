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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo uRepo ;

    @Autowired
    private JournalRepo journalRepo;
    public ResponseEntity<?> getByUsername(String username) {
        User user = uRepo.findByUsername(username);
        if(user != null) return new ResponseEntity<>(user,HttpStatus.OK);
        return new ResponseEntity<>("user not found",HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<?> createUser(User user) {
        user.setJouEntries(new ArrayList<JournalEntry>());
        user.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(uRepo.save(user),HttpStatus.CREATED);
    }


    @Transactional
    public ResponseEntity<?> editUser(ObjectId userId, User user) {
        User old = uRepo.findById(userId).orElse(null);
        if(old != null) {
            String un = user.getUsername();
            if (!un.isEmpty()) old.setUsername(un);
            String pw = user.getPassword();
            if (!pw.isEmpty()) old.setPassword(pw);
            return new ResponseEntity<>(uRepo.save(old), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("user not found",HttpStatus.EXPECTATION_FAILED);
    }

    @Transactional
    public ResponseEntity<?> delUser(String username){
        User old = uRepo.findByUsername(username);
        if(old != null) {
            List<JournalEntry> entries = old.getJouEntries();
            entries.forEach(journal -> journalRepo.deleteById(journal.getId()));
            uRepo.deleteById(old.getId());
            return new ResponseEntity<>("deleted",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("user not found",HttpStatus.CONFLICT);
    }
}
