package net.engineeringdigest.journalApp.service;

import com.sun.scenario.effect.Offset;
import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class JournalService {
    @Autowired
        private JournalRepository journalRepository;

    private static<T extends String> boolean check(T t){
        return t != null && !t.isEmpty();
    }

    public ResponseEntity<List<Journal>> getAllJournals(){
        return new ResponseEntity<>(journalRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Journal> getJournalById(ObjectId id){
        Optional<Journal> journal = journalRepository.findById(id);
        if(journal.isPresent()) {
            return new ResponseEntity<>(journal.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<Journal> addNewJournal(Journal journal){
        if(journal.getId() == null){
            journal.setLocalDateTime(LocalDateTime.now());
            Journal newJournal = journalRepository.save(journal);
            return new ResponseEntity<>(newJournal, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deleteJournalById(ObjectId id){
        Optional<Journal> existing = journalRepository.findById(id);
        if(!existing.isPresent()){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
        journalRepository.deleteById(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> editJournalById(ObjectId id, Journal journal){
        Optional<Journal> existing = journalRepository.findById(id);
        if(!existing.isPresent()){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

        if(check(journal.getTitle())) existing.get().setTitle(journal.getTitle());
        if(check(journal.getContent())) existing.get().setContent(journal.getContent());

        journalRepository.save(existing.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
