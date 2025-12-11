package net.engineeringdigest.journalApp.service;

import com.sun.scenario.effect.Offset;
import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
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
        return new ResponseEntity<>(journalRepository.findAll(), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Journal> getJournalById(ObjectId id){
        Optional<Journal> journal = journalRepository.findById(id);
        if(journal.isPresent()) {
            return new ResponseEntity<>(journal.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<Journal> addNewJournal(Journal journal){
        if(journal.getId() == null && journal.getDate() == null){
            journal.setDate(Date.from(Instant.now()));
            Journal newJournal = journalRepository.save(journal);
            return new ResponseEntity<>(newJournal, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<Boolean> deleteJournalById(ObjectId id){
        Optional<Journal> existing = journalRepository.findById(id);
        if(!existing.isPresent()){
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        journalRepository.deleteById(id);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Boolean> editJournalById(ObjectId id, Journal journal){
        Optional<Journal> existing = journalRepository.findById(id);
        if(!existing.isPresent()){
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        if(check(journal.getTitle())) existing.get().setTitle(journal.getTitle());
        if(check(journal.getContent())) existing.get().setContent(journal.getContent());

        journalRepository.save(existing.get());

        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
}
