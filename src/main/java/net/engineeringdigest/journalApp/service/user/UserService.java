package net.engineeringdigest.journalApp.service.user;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
            private UserRepository userRepository;

    @Autowired
            private JournalRepository journalRepository;

    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    @Transactional
    public ResponseEntity<UserEntity> createUser(UserEntity userEntity){
        if(     userEntity.getId() == null &&
                userEntity.getPassword() != null && userEntity.getUserName() != null &&
                !userRepository.findByUserName(userEntity.getUserName()).isPresent()
        ){
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRoles(Arrays.asList("USER"));
            userEntity.setLocalDateTime(LocalDateTime.now());
            userEntity.setJournals(new ArrayList<>());
            UserEntity saved = userRepository.save(userEntity);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Void> editUserById(UserEntity userEntity){
        if(userEntity.getId() != null) {
            Optional<UserEntity> old = userRepository.findById(userEntity.getId());
            if(old.isPresent()){
                if(userEntity.getUserName() != null && !userEntity.getUserName().trim().isEmpty()) old.get().setUserName(userEntity.getUserName());
                if(userEntity.getPassword() != null && !userEntity.getPassword().trim().isEmpty()) old.get().setPassword(passwordEncoder.encode(userEntity.getPassword()));
                userRepository.save(old.get());
                return new ResponseEntity<>( HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<Void> deleteUserByUserName(String userName){
        Optional<UserEntity> user = userRepository.findByUserName(userName);
        if(user.isPresent()) {
            List<Journal> journals = user.get().getJournals();
            if(!journals.isEmpty()) {
                journalRepository.deleteAll(journals);
            }
            userRepository.deleteByUserName(userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Journal>> getAllJournalsByUsername(String userName) {
        Optional<UserEntity> user = userRepository.findByUserName(userName);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get().getJournals(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
