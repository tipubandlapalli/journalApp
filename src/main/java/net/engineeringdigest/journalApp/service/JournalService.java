package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class JournalService {
    @Autowired
        private JournalRepository journalRepository;
    @Autowired
        private UserRepository userRepository;

    public ResponseEntity<List<Journal>> getAllJournals(){
        return new ResponseEntity<>(journalRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Journal> getJournalOfUserById(String id, String userName) {
        Optional<UserEntity> userEntity = userRepository.findByUserName(userName);
        if (userEntity.isPresent()) {
            Journal journal = userEntity.get().getJournals().stream().filter(j -> j.getId().equals(id)).findFirst().orElse(null);
            if (journal != null) return new ResponseEntity<>(journal, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<Journal> addNewJournal(Journal journal, String userName){
        Optional<UserEntity> userEntity = userRepository.findByUserName(userName);
        if (userEntity.isPresent()) {
            if(journal.getId() == null){
                journal.setLocalDateTime(LocalDateTime.now());
                Journal saved = journalRepository.save(journal);
                userEntity.get().getJournals().add(saved);
                userRepository.save(userEntity.get());
                return new ResponseEntity<>(saved, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<Void> deleteJournalById(String id, String userName){
        Optional<UserEntity> userEntity = userRepository.findByUserName(userName);
        if (userEntity.isPresent()) {
            boolean isDeleted = userEntity.get().getJournals().removeIf(j -> j.getId().equals(id));
            if(isDeleted) {
                journalRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<Void> editJournalById(String id, Journal journal, String userName){
        Optional<UserEntity> userEntity = userRepository.findByUserName(userName);
        if (userEntity.isPresent()) {
            Optional<Journal> existingJournal = userEntity.get().getJournals().stream().filter(j -> j.getId().equals(id)).findAny();
            if(existingJournal.isPresent()) {
                if(journal.getTitle() != null) existingJournal.get().setTitle(journal.getTitle());
                if(journal.getContent() != null) existingJournal.get().setContent(journal.getContent());
                journalRepository.save(existingJournal.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
