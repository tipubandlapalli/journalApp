package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repo.JournalRepo;
import net.engineeringdigest.journalApp.repo.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo uRepo ;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JournalRepo journalRepo;
    public ResponseEntity<?> getByUsername(String username) {
        User user = uRepo.findByUsername(username);
        if(user != null) return new ResponseEntity<>(user,HttpStatus.OK);
        return new ResponseEntity<>("user not found",HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<?> createUser(User user) {
        boolean isUserAlreadyExist = uRepo.existsByUsername(user.getUsername());
        if (isUserAlreadyExist) return new ResponseEntity<>("user exists already",HttpStatus.BAD_REQUEST);
        user.setJouEntries(new ArrayList<JournalEntry>());
        user.setLocalDateTime(LocalDateTime.now());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(Arrays.asList("ROLE_USER"));
        return new ResponseEntity<>(uRepo.save(user),HttpStatus.CREATED);
    }


    @Transactional
    public ResponseEntity<?> editUser(String username, User user) {
        if(uRepo.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>("user already exists",HttpStatus.CONFLICT);
        }
        User old = uRepo.findByUsername(username);
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

    public ResponseEntity<?> verify(User user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if(authentication.isAuthenticated()){
            return new ResponseEntity<>(jwtService.generateToken(user.getUsername()),HttpStatus.ACCEPTED);
        } else return new ResponseEntity<>("not authenticated",HttpStatus.BAD_REQUEST);
    }
}
