package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
            private UserRepository userRepository;
    @Autowired
            private JournalService journalService;

    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<UserEntity> findUserByUserName(String userName){
        Optional<UserEntity> user = userRepository.findByUserName(userName);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<UserEntity> createUser(UserEntity userEntity){
        if(userEntity.getId() == null){
            userEntity.setLocalDateTime(LocalDateTime.now());
            userEntity.setJournals(new ArrayList<>());
            UserEntity saved = userRepository.save(userEntity);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> editUserById(UserEntity userEntity){
        if(userEntity.getId() != null) {
            Optional<UserEntity> old = userRepository.findById(userEntity.getId());
            if(old.isPresent()){
                if(!userEntity.getUserName().isEmpty()) old.get().setUserName(userEntity.getUserName());
                if(!userEntity.getPassword().isEmpty()) old.get().setPassword(userEntity.getPassword());
                userRepository.save(old.get());
                return new ResponseEntity<>( HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<Void> deleteUserByUserName(String userName){
        Optional<UserEntity> user = userRepository.findByUserName(userName);
        if(user.isPresent()) {
            List<Journal> journals = user.get().getJournals();
            for(Journal journal: journals) {
                journalService.deleteJournalById(journal.getId());
            }
            userRepository.deleteByUserName(userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Journal>> getAllJournals(String userName) {
        Optional<UserEntity> user = userRepository.findByUserName(userName);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get().getJournals(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
